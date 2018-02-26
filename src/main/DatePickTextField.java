package main;

import com.qt.datapicker.DatePicker;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextField;

public class DatePickTextField extends JTextField
  implements Observer
{
  public DatePickTextField(int size)
  {
    super(size);
  }

  public void update(Observable o, Object arg) {
    Calendar calendar = (Calendar)arg;
    DatePicker dp = (DatePicker)o;
    setText(dp.formatDate(calendar));
  }
}