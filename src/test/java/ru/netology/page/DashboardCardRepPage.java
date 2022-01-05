package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardCardRepPage {

    private SelenideElement sum = $("[data-test-id=amount]").$("[class=input__control]");
    private SelenideElement fromWhich = $("[data-test-id=from]").$("[class=input__control]");
    private SelenideElement buttonRefill = $("[data-test-id=action-transfer");

    public DashboardCardPage refillCard(String sumRep, String fromRep) {
        sum.setValue(sumRep);
        fromWhich.setValue(fromRep);
        buttonRefill.click();
        return new DashboardCardPage();
    }
}
