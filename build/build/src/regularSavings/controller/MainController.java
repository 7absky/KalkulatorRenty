package regularSavings.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class MainController implements Initializable{

	public String appInfo = "Ten kalkulator pozwala obliczyć jaką sumę pieniędzy musisz odkładać co miesiąc, aby samemu zadbać o swoją emeryturę. "
			+ "Musisz podać tylko 3 wartości : "
			+ "\n-jak wysoką chcesz mieć emeryturę"
			+ "\n-ile lat masz zamiar oszczędzać"
			+ "\n-jakie jest oprocentowanie Twojej lokaty"
			+ "\n============================="
			+ "\nKalkulator oblicza wartości dla lokat o miesięcznej kapitalizacji odsetek. Uwzględnia on podatek od zysków kapitałowych,"
			+ " tzw. podatek Belki.";
	@FXML
    private TextField fvInfo;

    @FXML
    private Button clearButton;

    @FXML
    private Button calcButton;

    @FXML
    private TextField moneyTextField;

    @FXML
    private Button copyButton;

    @FXML
    private TextField rateInfo;

    @FXML
    private TextField rateTextField;

    @FXML
    private TextField pmtInfo;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField periodTextField;

    
    @FXML
    private void calculatePMT (ActionEvent event) {
    	
    	moneyTextField.setDisable(true); periodTextField.setDisable(true); rateTextField.setDisable(true);
    	
    	double BELKATAX = 0.19;
    	double monthsNum = 12.0;
  
    	double annuitySupplement = Double.parseDouble(moneyTextField.getText());
    	double savingPeriodMonths = Double.parseDouble(periodTextField.getText()) * monthsNum;
    	double annualPercRate = Double.parseDouble(rateTextField.getText());
    	
    	int yearlyAnnuity = (int)(annuitySupplement * monthsNum * (1+BELKATAX));
    	double fixedRate = Double.parseDouble(rateTextField.getText()) * (1-BELKATAX);
    	double fixedMonthlyRate = ((annualPercRate)/1200 )* (1- BELKATAX);
    	
    	// annual revenue from rates before-tax  
    	double a = fixedMonthlyRate + 1.0 ;
    	double power = Math.pow(a, monthsNum);
    	int futureValue =(int)((int)(annuitySupplement * monthsNum) / (power-1));
    	//PMT is the amount of cash saved each month
    	double pmtNumerator = futureValue * fixedMonthlyRate;
    	double pmtDenominator = (Math.pow(a, savingPeriodMonths +1.0)) - (1+fixedMonthlyRate);
    	int pmt = (int) (pmtNumerator / pmtDenominator);
    	
    	String calculationInfo = "";
    	calculationInfo+="Konieczne będzie odkładanie co miesiąc, "+pmt+" zł, przez "+periodTextField.getText()+" lat."
    			+ "\nŚrodki te będą musiały być wpłacane na lokatę o oprocentowaniu "+rateTextField.getText()+"% w skali roku"
    					+ ", która posiadać będzie miesięczną kapitalizację odsetek. "
    					+ "\nPo spełnieniu powyższych warunków, zgromadzisz kapitał w wysokości "+futureValue+" zł, który będzie rocznie "
    							+ "generował odsetki ("+yearlyAnnuity+"zł netto) w takiej wysokości, że będziesz mógł zapewnić sobie co miesiąc emeryturę w kwocie : "
    							+  moneyTextField.getText()+" zł.";

    	rateInfo.setText(""+fixedRate);
    	fvInfo.setText(""+futureValue);
    	pmtInfo.setText(""+pmt); pmtInfo.setDisable(false);
    	textArea.setText(calculationInfo);
    	
    }
    
    @FXML
    private void copyInfoArea (ActionEvent event) {
    	String copyText = textArea.getText();
    	final Clipboard clipboard = Clipboard.getSystemClipboard();
    	final ClipboardContent content = new ClipboardContent();
    	content.putString(copyText);
    	clipboard.setContent(content);
    }
    
    @FXML
    private void clearAll (ActionEvent event) {
    	moneyTextField.setText(""); 	moneyTextField.setPromptText("PLN"); 	moneyTextField.setDisable(false);
    	periodTextField.setText(""); 	periodTextField.setPromptText("lata"); 	periodTextField.setDisable(false);
    	rateTextField.setText(""); 		rateTextField.setPromptText("%"); 		rateTextField.setDisable(false);
    	pmtInfo.setText("");			pmtInfo.setDisable(true);
    	fvInfo.setText("");				fvInfo.setDisable(true);
    	rateInfo.setText("");			rateInfo.setDisable(true);
    	textArea.setText(appInfo);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		textArea.setText(appInfo);
		moneyTextField.setPromptText("PLN");
		periodTextField.setPromptText("lata");
		rateTextField.setPromptText("%");
		pmtInfo.setDisable(true);
		

	}

}

