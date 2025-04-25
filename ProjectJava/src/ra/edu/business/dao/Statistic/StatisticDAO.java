package ra.edu.business.dao.Statistic;

import java.util.List;
import java.util.Map;

public interface StatisticDAO {
        Map<Integer, Integer> getTotalCourseAndStudent();

        Map<String, Integer> getTotalStudentOfCourse();

        Map<String, Integer> getTop5CourseMostStudent();

        Map<String, Integer> getCourseMoreThan10Students();
}

