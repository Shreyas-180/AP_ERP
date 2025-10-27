public class Course {

    private String title;
    //private String instructor;
    private String code;
    private int credits;
    // weightage in percent
    private String course_description;
    private int quiz;
    private int assignment;
    private int midsem;
    private int endsem;
    private int group_project;
    private int seats;
    private String section;
    private Instructor prof;

/*code               | varchar(50)  | NO   | PRI | NULL    |       |
| title              | varchar(50)  | NO   | UNI | NULL    |       |
| credits            | int          | NO   |     | NULL    |       |
| instructor         | varchar(100) | NO   |     | NULL    |       |
| quiz               | int          | NO   |     | 0       |       |
| assignment         | int          | NO   |     | 0       |       |
| midsem             | int          | NO   |     | 0       |       |
| endsem             | int          | NO   |     | 0       |       |
| group_project      | int          | NO   |     | 0       |       |
| course_description | text         | YES  |     | NULL    |       | */
// have added seats and section

    Course(){
        
    }

    public Course(String code, String title, int credits, /*String instructor,*/ int quiz, int assignment, int midsem, int endsem, int group_project, String course_description, int seats, String section, Instructor prof){
        this.code = code;
        this.title = title;
        this.credits = credits;
        //this.instructor = instructor;
        this.quiz = quiz;
        this.assignment = assignment;
        this.midsem = midsem;
        this.endsem = endsem;
        this.group_project = group_project;
        this.course_description = course_description;
        this.seats = seats;
        this.section = section;
        this.prof = prof;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }

    public void setQuiz(int quiz) {
        this.quiz = quiz;
    }

    public void setAssignment(int assignment) {
        this.assignment = assignment;
    }

    public void setMidsem(int midsem) {
        this.midsem = midsem;
    }

    public void setEndsem(int endsem) {
        this.endsem = endsem;
    }

    public void setGroup_project(int group_project) {
        this.group_project = group_project;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setProf(Instructor prof) {
        this.prof = prof;
    }
    public void replace_prof(Instructor i1){
        this.prof = i1;
    }
    public Instructor edit_profinfo(){
        return prof;
    }
    public String getSection(){
        return section;
    }
    public String getTitle() {
        return title;
    }

    public String getInstructor() { // using this to match id in edit course
        return prof.get_name_id();
    }

    public String getCode() {
        return code;
    }

    public int getCredits() {
        return credits;
    }

    public String getCourse_description() {
        return course_description;
    }

    public int getQuiz() {
        return quiz;
    }

    public int getAssignment() {
        return assignment;
    }

    public int getMidsem() {
        return midsem;
    }

    public int getEndsem() {
        return endsem;
    }

    public int getGroup_project() {
        return this.group_project;
    }
    public int getseats(){
        return this.seats;
    }
}
