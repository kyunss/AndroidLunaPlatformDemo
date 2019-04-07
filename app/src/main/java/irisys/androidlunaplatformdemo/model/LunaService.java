package irisys.androidlunaplatformdemo.model;

import android.support.annotation.Nullable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

public interface LunaService {


//    @POST("/storage/descriptors")
//    Call<User> enrollPserson (@Body User user);

    @GET
    Observable<GetAccountResult> getAccount(@Url String url , @Header("Authorization") String loginPass);

//    @POST
//    Observable<CreatePersonResult> createPerson (@Url String url , @Header("Authorization") String loginPass, @Body CreatePersonRequest request);

    @POST("/storage/person")
    Observable<CreatePersonResult> createPerson(@Header("Authorization") String loginPass , @Body CreatePersonRequest request);

    @POST
    Observable<ResponseBody> createDiscriptor (@Url String url, @Header("Authorization") String login_pass, @Body RequestBody file);


    public class CreatePersonRequest{
        public String userData;

        public String getUserData() {
            return userData;
        }

        public void setUserData(String userData) {
            this.userData = userData;
        }
    }

    public class CreatePersonResult {
        public String personID;
        public int errorCode;
        public String detail;
    }

    public class GetAccountResult {
        public String email;
        public String organization_name;
        public boolean suspended;
        public int error_code;
        public String detail;
    }

    public class Person {
        private String firstName;
        private String middleName;
        private String lastName;
        private Boolean sex;
        private String identification;
        private String placeOfBirth;
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Nullable
        public Boolean getSex() {
            return sex;
        }

        public void setSex(@Nullable Boolean sex) {
            this.sex = sex;
        }

        public String getIdentification() {
            return identification;
        }

        public void setIdentification(String identification) {
            this.identification = identification;
        }

        public String getPlaceOfBirth() {
            return placeOfBirth;
        }

        public void setPlaceOfBirth(String placeOfBirth) {
            this.placeOfBirth = placeOfBirth;
        }
    }
}
