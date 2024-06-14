package com.example.quiz.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.OptionType;
import com.example.quiz.constants.ResMessage;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.DeleteReq;
import com.example.quiz.vo.FillinReq;
import com.example.quiz.vo.Question;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.vo.SearchRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Override
	public BasicRes createOrUpdate(CreateOrUpdateReq req) {
		// �z�酢��
		BasicRes checkResult = checkParams(req);
		// checkResult == null �r����ʾ�����z�鶼���_
		if (checkResult != null) {
			return checkResult;
		}
		// ��� Quiz �е� questions ���Y�ϸ�ʽ�� String������Ҫ�� req �� List<Question> �D�� String
		// ͸�^ ObjectMapper ���Ԍ����(e)�D�� JSON ��ʽ�е��ִ�
		ObjectMapper mapper = new ObjectMapper();
		try {
			String questionStr = mapper.writeValueAsString(req.getQuestionsList());

			// �� req �е� id > 0����ʾ�����Ѵ��ڵ��Y��; �� id = 0����ʾ�������Y��
			if (req.getId() > 0) {
				// req.getId() > 0 ��ʾ req �� id
				// ���ɷN��ʽ��һ
				// ʹ�÷���1��͸�^ findbyId�������Y�ϣ��؂�ԓ�Y��
				// ʹ�÷���2�������͸�^ existById ���Д��Y���Ƿ���ڣ����Ի؂����Y�����h��ֻ����һ�� bit (0��1)
				// ����1.͸�^ findById: �����Y�ϣ��؂�ԓ�Y��
				Optional<Quiz> op = quizDao.findById(req.getId());
				// �Д��Ƿ����Y��
//				if (op.isEmpty()) { // op.isEmpty(): ��ʾ�]�Y��
//					return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
//							ResMessage.UPDATE_ID_NOT_FOUND.getMassage());
//				}
//				Quiz quiz = op.get();
//				// �O����ֵ(ֵ�� req ��)
//				// �� req �е���ֵ�O�����f�� quiz�У����O�� id����� id һ��
//				quiz.setName(req.getName());
//				quiz.setDescription(req.getDescription());
//				quiz.setStartDate(req.getStartDate());
//				quiz.setEndDate(req.getEndDate());
//				quiz.setQuestions(questionStr);
//				quiz.setPublished(req.isPublished());
				// ���� 2. ͸�^ existById: �؂�һ�� bit ��ֵ
				// �@߅Ҫ�Д��� req ���M��� id �Ƿ�����д���� DB ��
				// ��� id �������ڣ��ֲ��z�飬���m��ʽ�a�ں��� JPA �� save �����r����׃������
				boolean boo = quizDao.existsById(req.getId());
				if (!boo) { // !boo: ��ʾ�Y�ϲ�����
					return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
				}
			}
			// ==============================
			// ����һ���� if ��ʽ�a���Կs�p�������@��
//			   if(req.getId() > 0 && !quizDao.existsById(req.getId())) {   
//			    return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), 
//			      ResMessage.UPDATE_ID_NOT_FOUND.getMessage());   
//			   }
			// ==============================
//			Quiz quiz = new Quiz(req.getName(), req.getDescription(), req.getStartDate(),//
//						req.getEndDate(), questionStr, req.isPublished());
//			quizDaosave(quiz)			
			// ���׃�� quiz ֻʹ��һ�Σ����Կ���ʹ������e׫��(����Ҫ׃��)
			// new Quiz() �Ў��� req.getId() �� PK���ں��� save �r������ȥ�z�� PK �Ƿ��д���� DB ��
			// ����]�Єt����һ�P�Y�� ; ����Єt����ԓ�P�Y��
			// req �Л]��ԓ��λ�r���A�O�� 0����� id ���Y���͑B�� int
			quizDao.save(new Quiz(req.getId(), req.getName(), req.getDescription(), req.getStartDate(), //
					req.getEndDate(), questionStr, req.isPublished()));

		} catch (JsonProcessingException e) {
			return new BasicRes(ResMessage.JSON_PROCESSING_EXCEPTION.getCode() //
					, ResMessage.JSON_PROCESSING_EXCEPTION.getMessage());

		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());

	}

	private BasicRes checkParams(CreateOrUpdateReq req) {
		// �z�醖�텢��
		// StringUtils.hasText(�ִ�):���z���ִ��Ƿ�� null�����ִ���ȫ�հ��ִ������Ƿ���3�N����һ헣��t�؂�false
		// ǰ����@�@̖�鷴�����˼�����ִ��z��Y���� false ��Ԓ���͕��M�� if �Č����^�K
		// !StringUtils.hasText(account) ��ͬ� StringUtils.hasText(account) == false
		if (!StringUtils.hasText(req.getName())) {
			return new BasicRes(ResMessage.PARAM_QUIZ_NAME_ERROR.getCode(), //
					ResMessage.PARAM_QUIZ_NAME_ERROR.getMessage());

		}
		if (!StringUtils.hasText(req.getDescription())) {
			return new BasicRes(ResMessage.PARAM_DESCRIPTION_ERROR.getCode(), //
					ResMessage.PARAM_DESCRIPTION_ERROR.getMessage());

		}
		// LocalDate.now(): ȡ��ϵ�y��ǰ�r�g
		// req.getStartDate().isBefore(LocalDate.now()): �� req �е��_ʼ�r�g�Ȯ�ǰ�r�g�����õ� true
		// !req.getStartDate().isEqual(LocalDate.now()): ǰ���м��@�@̖����ʾ���õ��෴�ĽY���������_ʼ�r�g
		// ��С춵�춮�ǰ�r�g
		if (req.getStartDate() == null || !req.getStartDate().isAfter(LocalDate.now())) {
			return new BasicRes(ResMessage.PARAM_START_DATE_ERROR.getCode(), //
					ResMessage.PARAM_START_DATE_ERROR.getMessage());
		}
		// ��ʽ�a��ֱ�е��@�Еr����ʾ�_ʼ�r�gһ����춮�ǰ�r�g
		// �������m�z��Y���r��ֻҪ�_���Y���r�g��춵���_ʼ�r�g�ͺã����ֻҪ�Y���r�g��춵���_ʼ�r�g��
		// ��һ�����Ǵ�춮�ǰ�r�g
		// �_ʼ�r�g >= ��ǰ�r�g : �Y���r�gһ�� >= ��ǰ�r�g ==> �Y���r�g >= �_ʼ�r�g>= ��ǰ�r�g
		// ���Բ���Ҫ�Д� !req.getEndDate().isAfter(LocalDate.now())
		// 1. �Y���r�g����С춵�춮�ǰ�r�g
		// 2. �Y���r�g����С��_ʼ�r�g
		if (req.getEndDate() == null || req.getEndDate().isBefore(req.getStartDate())) {
			return new BasicRes(ResMessage.PARAM_END_DATE_ERROR.getCode(), //
					ResMessage.PARAM_END_DATE_ERROR.getMessage());

		}
		// �z�醖�}����
		if (CollectionUtils.isEmpty(req.getQuestionsList())) {
			return new BasicRes(ResMessage.PARAM_QUESTION_LIST_NOT_FOUND.getCode(), //
					ResMessage.PARAM_QUESTION_LIST_NOT_FOUND.getMessage());
		}
		// һ��������ܕ��ж������}����˙z��ÿ�����}�ą���
		for (Question item : req.getQuestionsList()) {
			if (item.getId() <= 0) {
				return new BasicRes(ResMessage.PARAM_QUESTION_ID_ERROR.getCode(), //
						ResMessage.PARAM_QUESTION_ID_ERROR.getMessage());
			}
			if (!StringUtils.hasText(item.getTitle())) {
				return new BasicRes(ResMessage.PARAM_TITLE_ERROR.getCode(), //
						ResMessage.PARAM_TITLE_ERROR.getMessage());
			}

			if (!StringUtils.hasText(item.getType())) {
				return new BasicRes(ResMessage.PARAM_TYPE_ERROR.getCode(), //
						ResMessage.PARAM_TYPE_ERROR.getMessage());
			}
			// �� Option type �ǆ��x����x�r��Option ���ܞ���ִ�
			// �� Option type �����֕r��Option ���S����ִ�
			// ���l���z��: �� Option type �ǆ��x����x�r���� Option ����ִ���return false
			if (item.getType().equals(OptionType.SINGLE_CHOICE.getType())//
					|| item.getType().equals(OptionType.MULTIPLE_CHOICE.getType())) {
				if (!StringUtils.hasText(item.getOption())) {
					return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
							ResMessage.PARAM_OPTIONS_ERROR.getMessage());
				}
			}
			// ����������2�� if �ρ㌑��:(�l��1 || �l��2) && �l��3)
//			if(item.getType().equals(OptionType.SINGLE_CHOICE.getType())||
//					item.getType().equals(OptionType.MULTIPLE_CHOICE.getType())
//					&& StringUtils.hasText(item.getOption())){
//				return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
//						ResMessage.PARAM_OPTIONS_ERROR.getMassage());
//			}

		}
		return null;
//		return new BasicRes(ResMessage.SUCCESS.getCode(), //
//				ResMessage.SUCCESS.getMassage());

	}

	@Override
	public SearchRes search(SearchReq req) {
		String name = req.getName();
		LocalDate start = req.getStartDate();
		LocalDate end = req.getEndDate();
		// ���O name �� null ����ȫ�հ׵��ִ�������ҕ��]��ݔ��l��ֵ����ʾҪȡ��ȫ��
		// JPA �� containing �������l��ֵ�ǿ��ִ��r�����ь�ȫ��
		// ����Ҫ�� name ��ֵ�� null ��ȫ�հ��ִ��r���D�Q�ɿ��ִ�
		if (!StringUtils.hasText(name)) {
			name = "";
		}
		if (start == null) {
			start = LocalDate.of(1970, 1, 1);
		}
		if (end == null) {
			end = LocalDate.of(2999, 12, 31);
		}
//		List<Quiz> res = quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(name, start, end);
//		return new SearchRes(ResMessage.SUCCESS.getCode(), 
//				ResMessage.SUCCESS.getMassage(), res);
		return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
				quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(name, start, end));
	}

	@Override
	public BasicRes delete(DeleteReq req) {
		// �z�酢��
		if (!CollectionUtils.isEmpty(req.getIdList())) {
			// �h������
			try {
				quizDao.deleteAllById(req.getIdList());
			} catch (Exception e) {
				// �� deleteAllById �����У�id ��ֵ�����ڕr��JPA �����e
				// ����ڄh��֮ǰ��JPA �����ь������ id ֵ�����]�Y���͕����e
				// ��춌��H�ϛ]�h���κ��Y�ϣ���˾Ͳ���Ҫ���@�� Exception ��̎��
			}
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());

	}

	@Override
	public BasicRes fillin(FillinReq req) {
		// TODO Auto-generated method stub
		return null;
	}
}
