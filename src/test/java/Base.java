import io.restassured.RestAssured;

public class Base {

    public void setBaseSettings() {
        RestAssured.baseURI = "https://superhero.qa-test.csssr.com/superheroes";
    }

    public Base(){
        setBaseSettings();
    }

}
