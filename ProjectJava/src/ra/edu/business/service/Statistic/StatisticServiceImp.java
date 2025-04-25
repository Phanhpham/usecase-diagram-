package ra.edu.business.service.Statistic;

import ra.edu.business.dao.Statistic.StatisticDAO;
import ra.edu.business.dao.Statistic.StatisticDAOImp;

import java.util.List;
import java.util.Map;

public class StatisticServiceImp implements  StatisticService{
    private StatisticDAO statisticDao;

    public StatisticServiceImp() {
        statisticDao = new StatisticDAOImp();
    }

    @Override
    public Map<Integer, Integer> getTotalCourseAndStudent() {
        return statisticDao.getTotalCourseAndStudent();
    }
    @Override
    public Map<String, Integer> getTotalStudentOfCourse() {
        return statisticDao.getTotalStudentOfCourse();
    }

    @Override
    public Map<String, Integer> getTop5CourseMostStudent() {
        return statisticDao.getTop5CourseMostStudent();
    }

    @Override
    public Map<String, Integer> getCourseMoreThan10Students() {
        return statisticDao.getCourseMoreThan10Students();
    }
}
