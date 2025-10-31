public class Admin extends User {
    //String name;
    String id;
    
    public Admin(String id, String a){
        super(a);
        this.id = id;
    }
    public String getid(){
        return this.id;
    }
    public String get_real_name(){
        return this.getUsername();
    }
        // public void setname(String name){
    //     super(name);
    // }
}
