import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This is just a demo for you, please run it on JDK17 (some statements may be not allowed in lower version).
 * This is just a demo, and you can extend and implement functions
 * based on this demo, or implement it in a different way.
 */
public class OnlineCoursesAnalyzer {

    List<Course> courses = new ArrayList<>();

    public OnlineCoursesAnalyzer(String datasetPath) {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(datasetPath, StandardCharsets.UTF_8));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
                Course course = new Course(info[0], info[1], new Date(info[2]), info[3], info[4], info[5],
                        Integer.parseInt(info[6]), Integer.parseInt(info[7]), Integer.parseInt(info[8]),
                        Integer.parseInt(info[9]), Integer.parseInt(info[10]), Double.parseDouble(info[11]),
                        Double.parseDouble(info[12]), Double.parseDouble(info[13]), Double.parseDouble(info[14]),
                        Double.parseDouble(info[15]), Double.parseDouble(info[16]), Double.parseDouble(info[17]),
                        Double.parseDouble(info[18]), Double.parseDouble(info[19]), Double.parseDouble(info[20]),
                        Double.parseDouble(info[21]), Double.parseDouble(info[22]));
                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //1
    public Map<String, Integer> getPtcpCountByInst() {

        Map<String, Integer> m = new LinkedHashMap<String, Integer>();
        Map<String, Integer> m2 = new LinkedHashMap<String, Integer>();
        for (Course c : courses) {
            if (!m.containsKey(c.institution))
                m.put(c.institution, c.participants);
            else m.put(c.institution, m.get(c.institution) + c.participants);
        }
        m.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByKey()).forEachOrdered(e -> m2.put(e.getKey(), e.getValue()));
        return m2;
    }

    //2
    public Map<String, Integer> getPtcpCountByInstAndSubject() {
        Map<String, Integer> m = new LinkedHashMap<String, Integer>();
        Map<String, Integer> m2 = new LinkedHashMap<String, Integer>();
        for (Course c : courses) {
            String s = c.institution + "-" + c.subject;
            if (!m.containsKey(s))
                m.put(s, c.participants);
            else m.put(s, m.get(s) + c.participants);
        }
        m.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEachOrdered(e -> m2.put(e.getKey(), e.getValue()));
        return m2;
    }

    //3
    public Map<String, List<List<String>>> getCourseListOfInstructor() {
        Map<String, List<List<String>>> m = new HashMap<>();
        for (Course c : courses) {
            String[] strings = c.instructors.split(",");
            int t = 1;
            if (strings.length == 0) continue;
            if (strings.length == 1) t = 0;
            for (String s : strings) {
                s=s.trim();
                if (!m.containsKey(s)) {
                    ArrayList<List<String>> a = new ArrayList<List<String>>();
                    m.put(s, a);
                    a.add(new ArrayList<String>());//l1
                    a.add(new ArrayList<String>());//l2
                    a.get(t).add(c.title);
                } else {
                    if (!m.get(s).get(t).contains(c.title))
                        m.get(s).get(t).add(c.title);
                }
            }
        }
        m.forEach((key, value) -> Collections.sort(value.get(0)));
        m.forEach((key, value) -> Collections.sort(value.get(1)));
        return m;

    }

    //4
    public List<String> getCourses(int topK, String by) {
        return null;
    }

    //5
    public List<String> searchCourses(String courseSubject, double percentAudited, double totalCourseHours) {
        return null;
    }

    //6
    public List<String> recommendCourses(int age, int gender, int isBachelorOrHigher) {
        return null;
    }

}

class Course {
    String institution;
    String number;
    Date launchDate;
    String title;
    String instructors;
    String subject;
    int year;
    int honorCode;
    int participants;
    int audited;
    int certified;
    double percentAudited;
    double percentCertified;
    double percentCertified50;
    double percentVideo;
    double percentForum;
    double gradeHigherZero;
    double totalHours;
    double medianHoursCertification;
    double medianAge;
    double percentMale;
    double percentFemale;
    double percentDegree;

    public Course(String institution, String number, Date launchDate,
                  String title, String instructors, String subject,
                  int year, int honorCode, int participants,
                  int audited, int certified, double percentAudited,
                  double percentCertified, double percentCertified50,
                  double percentVideo, double percentForum, double gradeHigherZero,
                  double totalHours, double medianHoursCertification,
                  double medianAge, double percentMale, double percentFemale,
                  double percentDegree) {
        this.institution = institution;
        this.number = number;
        this.launchDate = launchDate;
        if (title.startsWith("\"")) title = title.substring(1);
        if (title.endsWith("\"")) title = title.substring(0, title.length() - 1);
        this.title = title;
        if (instructors.startsWith("\"")) instructors = instructors.substring(1);
        if (instructors.endsWith("\"")) instructors = instructors.substring(0, instructors.length() - 1);
        this.instructors = instructors;
        if (subject.startsWith("\"")) subject = subject.substring(1);
        if (subject.endsWith("\"")) subject = subject.substring(0, subject.length() - 1);
        this.subject = subject;
        this.year = year;
        this.honorCode = honorCode;
        this.participants = participants;
        this.audited = audited;
        this.certified = certified;
        this.percentAudited = percentAudited;
        this.percentCertified = percentCertified;
        this.percentCertified50 = percentCertified50;
        this.percentVideo = percentVideo;
        this.percentForum = percentForum;
        this.gradeHigherZero = gradeHigherZero;
        this.totalHours = totalHours;
        this.medianHoursCertification = medianHoursCertification;
        this.medianAge = medianAge;
        this.percentMale = percentMale;
        this.percentFemale = percentFemale;
        this.percentDegree = percentDegree;

    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructors() {
        return instructors;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHonorCode() {
        return honorCode;
    }

    public void setHonorCode(int honorCode) {
        this.honorCode = honorCode;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getAudited() {
        return audited;
    }

    public void setAudited(int audited) {
        this.audited = audited;
    }

    public int getCertified() {
        return certified;
    }

    public void setCertified(int certified) {
        this.certified = certified;
    }

    public double getPercentAudited() {
        return percentAudited;
    }

    public void setPercentAudited(double percentAudited) {
        this.percentAudited = percentAudited;
    }

    public double getPercentCertified() {
        return percentCertified;
    }

    public void setPercentCertified(double percentCertified) {
        this.percentCertified = percentCertified;
    }

    public double getPercentCertified50() {
        return percentCertified50;
    }

    public void setPercentCertified50(double percentCertified50) {
        this.percentCertified50 = percentCertified50;
    }

    public double getPercentVideo() {
        return percentVideo;
    }

    public void setPercentVideo(double percentVideo) {
        this.percentVideo = percentVideo;
    }

    public double getPercentForum() {
        return percentForum;
    }

    public void setPercentForum(double percentForum) {
        this.percentForum = percentForum;
    }

    public double getGradeHigherZero() {
        return gradeHigherZero;
    }

    public void setGradeHigherZero(double gradeHigherZero) {
        this.gradeHigherZero = gradeHigherZero;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public double getMedianHoursCertification() {
        return medianHoursCertification;
    }

    public void setMedianHoursCertification(double medianHoursCertification) {
        this.medianHoursCertification = medianHoursCertification;
    }

    public double getMedianAge() {
        return medianAge;
    }

    public void setMedianAge(double medianAge) {
        this.medianAge = medianAge;
    }

    public double getPercentMale() {
        return percentMale;
    }

    public void setPercentMale(double percentMale) {
        this.percentMale = percentMale;
    }

    public double getPercentFemale() {
        return percentFemale;
    }

    public void setPercentFemale(double percentFemale) {
        this.percentFemale = percentFemale;
    }

    public double getPercentDegree() {
        return percentDegree;
    }

    public void setPercentDegree(double percentDegree) {
        this.percentDegree = percentDegree;
    }
}