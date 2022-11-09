package kleicreator.sdk;

public interface Plugin {
    default String Name(){
        return this.getClass().getSimpleName();
    }
    default String Id(){
        return this.getClass().getSimpleName().toLowerCase();
    }
    default String Author(){
        return "";
    }
}
