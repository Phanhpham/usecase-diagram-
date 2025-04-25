package ra.edu.business.service.Statistic;

import java.util.List;
import java.util.Map;

public interface StatisticService {
    Map<Integer, Integer> getTotalCourseAndStudent();

    Map<String, Integer> getTotalStudentOfCourse();

    Map<String, Integer> getTop5CourseMostStudent();

    Map<String, Integer> getCourseMoreThan10Students();
}
