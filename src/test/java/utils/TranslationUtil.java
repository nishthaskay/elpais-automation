package utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class TranslationUtil {
    private static final String RAPIDAPI_KEY = System.getenv("RAPIDAPI_KEY");
    private static final String RAPIDAPI_HOST = "google-translate113.p.rapidapi.com";
    public static String translateToEnglish(String text) {

        RestAssured.baseURI = "https://google-translate113.p.rapidapi.com/language/translate/v2";
        Response response = RestAssured.given()
                .header("X-RapidAPI-Key", RAPIDAPI_KEY)
                .header("X-RapidAPI-Host", RAPIDAPI_HOST)
                .header("Content-Type", "application/json")
                .formParam("q", text)
                .formParam("target", "en")
                .post();

        if (response.statusCode() == 200) {
            JsonPath jsonPath = response.jsonPath();
            return jsonPath.getString("data.translations[0].translatedText");
        } else {
            System.err.println("Translation failed with status code: " + response.statusCode());
            System.err.println("Response: " + response.getBody().asString());
            return null;
        }

    }
}

