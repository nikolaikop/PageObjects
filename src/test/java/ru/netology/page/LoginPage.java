package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement fieldLogin = $("[data-test-id=login] input");
    private SelenideElement fieldPw = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo){
        fieldLogin.setValue(authInfo.getLogin());
        fieldPw.setValue(authInfo.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}
