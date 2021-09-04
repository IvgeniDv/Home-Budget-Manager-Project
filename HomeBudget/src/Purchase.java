import java.util.*;

public class Purchase {
    // Var declaration
    protected String _purchaseName;
    protected String _purchasePeriod;
    protected boolean _creditPayIndicator;
    protected double _purchaseAmount;

    // Constructors
    public Purchase(){
        _purchaseName = "New Purchase";
        _purchaseAmount = 0;
        _creditPayIndicator = false;
    }

    public Purchase(String name, double amount){
        _purchaseName = name;
        _purchaseAmount = amount;
        _creditPayIndicator = false;
    }

    public Purchase(String name, double amount, boolean creditIndicator){
        _purchaseName = name;
        _purchaseAmount = amount;
        _creditPayIndicator = creditIndicator;
    }

    // Getters
    protected boolean getCreditPayIndicator(){
        return _creditPayIndicator;
    }

    protected String getPurchaseName(){
        return  _purchaseName;
    }

    protected  double getPurchaseAmount(){
        return _purchaseAmount;
    }

    // Setters
    protected void setCreditPayIndicator(boolean creditPayIndicator){
        _creditPayIndicator = creditPayIndicator;
    }

    protected void setPurchaseName(String name){
        _purchaseName = name;
    }

    protected void setPurchaseAmount(double amount){
        _purchaseAmount = amount;
    }

    protected void printPurchase(){
        if (this.getCreditPayIndicator())
            System.out.println(_purchaseName + " " + _purchaseAmount + " Nis - via credit");
        else
            System.out.println(_purchaseName + " " + _purchaseAmount + " Nis");
    }















}
