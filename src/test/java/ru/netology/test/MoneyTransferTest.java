package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void before() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenPersonalCardsFrom2To1() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();      //получение логина и пароля
        var verificationPage = loginPage.validLogin(authInfo);   //ввод данных
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);  //получение кода СМС
        var dashboardCardPage = verificationPage.validVerify(verificationCode);  //ввод смс
        String sumRep = "500";  //сумма пополнения
        val balance_1_CardBefoRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard1().getNumber());  //получение баланса 1 карты до перевода
        val balance_2_CardBefoRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard2().getNumber());  //получение баланса 2 карты до перевода
        val dashboardCardRepPage = dashboardCardPage.refillCard(DataHelper.getCard1().getNumber()); //выбор карты для пополнения
        dashboardCardRepPage.refillCard(sumRep, DataHelper.getCard2().getNumber());  //ввод данных
        val balance_1_CardAfteRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard1().getNumber());  //получение баланса
        val balance_2_CardAfteRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard2().getNumber());  //получение баланса
        int sum_Rep = Integer.parseInt(sumRep);
        assertEquals(balance_1_CardBefoRep + sum_Rep, balance_1_CardAfteRep);
        assertEquals(balance_2_CardBefoRep - sum_Rep, balance_2_CardAfteRep);
    }


    @Test
    void shouldTransferMoneyBetweenPersonalCardsFrom1To2() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();      //получение логина и пароля
        var verificationPage = loginPage.validLogin(authInfo);   //ввод данных
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);  //получение кода СМС
        var dashboardCardPage = verificationPage.validVerify(verificationCode);  //ввод смс
        String sumRep = "500";  //сумма пополнения
        val balance_1_CardBefoRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard1().getNumber());  //получение баланса 1 карты до перевода
        val balance_2_CardBefoRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard2().getNumber());  //получение баланса 2 карты до перевода
        val dashboardCardRepPage = dashboardCardPage.refillCard(DataHelper.getCard2().getNumber()); //выбор карты для пополнения
        dashboardCardRepPage.refillCard(sumRep, DataHelper.getCard1().getNumber());  //ввод данных
        val balance_1_CardAfteRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard1().getNumber());  //получение баланса
        val balance_2_CardAfteRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard2().getNumber());  //получение баланса
        int sum_Rep = Integer.parseInt(sumRep);
        assertEquals(balance_1_CardBefoRep - sum_Rep, balance_1_CardAfteRep);
        assertEquals(balance_2_CardBefoRep + sum_Rep, balance_2_CardAfteRep);
    }


    @Test
    void shouldntDepositAmountExceedsActualAmount() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();      //получение логина и пароля
        var verificationPage = loginPage.validLogin(authInfo);   //ввод данных
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);  //получение кода СМС
        var dashboardCardPage = verificationPage.validVerify(verificationCode);  //ввод смс
        val balance_1_CardBefoRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard1().getNumber());  //получение баланса 1 карты до перевода
        val balance_2_CardBefoRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard2().getNumber());  //получение баланса 2 карты до перевода
        int sumRepPlus=balance_1_CardBefoRep+800;// получение сууммы превышающей остаток на счету
        val dashboardCardRepPage = dashboardCardPage.refillCard(DataHelper.getCard2().getNumber()); //выбор карты для пополнения
        dashboardCardRepPage.refillCard(String.valueOf(sumRepPlus), DataHelper.getCard1().getNumber());  //ввод данных
        val balance_1_CardAfteRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard1().getNumber());  //получение баланса
        val balance_2_CardAfteRep = dashboardCardPage.infoBalanceCard(DataHelper.getCard2().getNumber());  //получение баланса
        assertEquals(balance_1_CardBefoRep, balance_1_CardAfteRep);
        assertEquals(balance_2_CardBefoRep, balance_2_CardAfteRep);
    }
}
