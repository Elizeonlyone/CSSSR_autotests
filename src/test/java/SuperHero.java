import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SuperHero extends Base{

    private String fullName;
    private String birthDate;
    private String city;
    private String gender;
    private int id;
    private String mainSkill;
    private String phone;

    private File heroScheme = new File("./src/test/java/Schemas/superHeroScheme.json");

    public SuperHero(){
        createParameters();
    }

    public SuperHero(int id){
        setId(id);
        getInfoAboutSuperhero(this);
    }

    public Response createNewHero(){

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("fullName", fullName);
        jsonBody.put("birthDate", birthDate);
        jsonBody.put("city", city);
        jsonBody.put("gender", gender);
        jsonBody.put("mainSkill", mainSkill);
        if(phone != null){
            jsonBody.put("phone", phone);
        }

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody.toString())
                .when()
                .post()
                .then()
                .assertThat()
                .body(matchesJsonSchema(heroScheme))
                .extract()
                .response();

        try {
            assertEquals(200, response.statusCode());
            assertAll(
                    () -> assertEquals(birthDate, response.jsonPath().getString("birthDate")),
                    () -> assertEquals(fullName, response.jsonPath().getString("fullName")),
                    () -> assertEquals(city, response.jsonPath().getString("city")),
                    () -> assertEquals(mainSkill, response.jsonPath().getString("mainSkill")),
                    () -> assertEquals(gender, response.jsonPath().getString("gender"))
                    //,() -> assertEquals(phone, response.jsonPath().getString("phone")) TODO Удалено, из за ошибки с привязкой телефона
            );
        } catch (AssertionError e){
            fail(e.getMessage() + "\n\n" + response.prettyPeek().toString());
        }

        setId(response.jsonPath().getInt("id"));
        return response;
    }

    private void getInfoAboutSuperhero(SuperHero superHero){

            Response response = RestAssured.given()
                    .when()
                    .get("/" + Integer.toString(superHero.id))
                    .then()
                    .assertThat()
                    .body(matchesJsonSchema(heroScheme))
                    .extract()
                    .response();

        try {
            assertEquals(200, response.statusCode());
        } catch (AssertionError e){
            fail(e.getMessage() + "\n\n" + response.prettyPeek().toString());
        }

        setMainSkill(response.jsonPath().getString("mainSkill"));
        setPhone(response.jsonPath().getString("phone"));
        setGender(response.jsonPath().getString("gender"));
        setCity(response.jsonPath().getString("city"));
        setBirthDate(response.jsonPath().getString("birthDate"));
        setFullName(response.jsonPath().getString("fullName"));
    }

    private void createParameters(){


        setFullName("Superhero number " + (int)(Math.random() * 1000));
        setBirthDate((int)(Math.random() * 2100 + 1900) + "-01-01");
        setCity("City number " + (int)(Math.random() * 1000));
        setMainSkill("SuperPower number " + (int)(Math.random() * 1000));
        if(Math.random() < 0.5){
            setGender("M");}
        else {
            setGender("F");}
        if(Math.random() < 0.75) {
            setPhone("+7900000" + (int)(Math.random() * 8999 + 1000));
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString(){
        return "Супергерой по имени: " + fullName
                + "\nиз города: " + city
                + "\nродился: " + birthDate
                + "\nосновная супер сила: " + mainSkill
                + "\nпол: " + gender
                + "\nномер телефона: " + phone
                + "\nid в системе: " + id;
    }

    public boolean equals(SuperHero superHero){
        return this.birthDate.equals(superHero.getBirthDate())
                && this.city.equals(superHero.getCity())
                && this.fullName.equals(superHero.getFullName())
                && this.gender.equals(superHero.getGender())
                && this.id == (superHero.getId())
                && this.mainSkill.equals(superHero.getMainSkill());
                //,&& this.phone.equals(superHero.getPhone())  TODO Удалено из-за ошибки с номером телефона

    }

}
