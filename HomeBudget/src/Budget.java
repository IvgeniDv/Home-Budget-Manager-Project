import java.util.*;

public class Budget {
    // Var declaration
    protected String _budgetName;
    protected String _billingPeriod;
    protected double _initialValue;
    protected double _totalLeft;

    // Constructors
    public Budget(){
        _budgetName = "New Budget";
        _initialValue = 0;
        _totalLeft = _initialValue;
    }

    public Budget(String name, double amount){
        _budgetName = name;
        _initialValue = amount;
        _totalLeft = _initialValue;
    }

    // Setters
    protected  void setName(String name){
        _budgetName = name;
    }

    protected  void setInitialValue(double initialValue){
        _initialValue = initialValue;
    }

    protected  void setTotalLeftValue(double newValue){
        _totalLeft = newValue;
    }

    // Getters
    protected String getName(){
        return  _budgetName;
    }

    protected double getTotalLeft(){
        return _totalLeft;
    }

    protected double getInitialValue(){
        return _initialValue;
    }

    public void printMyBudget(){
        System.out.println("Budget Name: " + _budgetName);
        System.out.println("Budget initial amount: " + _initialValue);
        System.out.println("Total left in budget: " + _totalLeft);
    }

    protected void addPurchase(double purchaseAmount){
        _totalLeft = _totalLeft - purchaseAmount;
    }

    protected void removePurchase(double purchaseAmount){
        _totalLeft = _totalLeft + purchaseAmount;
    }









}
