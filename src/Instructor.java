import java.util.ArrayList;

public class Instructor extends User{
    //private int num_sections;
    private ArrayList<String> sections;
    private ArrayList<String> courses;
    String department;
    private String id;
    
    Instructor(String name, String id){
        super(name);
        this.sections = new ArrayList<>();  
        this.courses = new ArrayList<>();   
        this.id = id;
    }
    public int getNum_sections(){
        return sections.size();
    }
    public String getid(){
        return this.id;
    }
    public void add_section(String sec){
        this.sections.add(sec);
    }
    public void add_course(String course){
        this.courses.add(course);
    }
    public void remove_course(String course){
        this.courses.remove(course);
    }
    public void remove_section(String section){
        this.sections.remove(section);
    }
    public String get_name_id(){
        return this.id;
    }
    public String getSections(){
        String temp = "";
        if(sections.isEmpty()){
            temp += "Empty";
        }
        else{
            for(int i = 0; i < sections.size(); i++){
                temp += sections.get(i);
            }
        }
        return temp;
    }
  

}
