import java.util.ArrayList;

public class Instructor extends User{
    //private int num_sections;
    private ArrayList<String> sections;
    private ArrayList<String> courses;
    String department;
    
    Instructor(String name){
        super(name);
        this.sections = new ArrayList<>();  
        this.courses = new ArrayList<>();   
    }
    public int getNum_sections(){
        return sections.size();
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
