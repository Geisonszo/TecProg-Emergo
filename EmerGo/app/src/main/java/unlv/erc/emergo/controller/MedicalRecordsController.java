/********************
 * Class name: MedicalRecordsController (.java)
 *
 * Purpose: The purpose of this class is to register the medical file of the user.
 ********************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import dao.UserDao;
import helper.MaskHelper;
import unlv.erc.emergo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalRecordsController extends Activity {

  private EditText fullName;
  private EditText birthday;
  private EditText observations;

  private Spinner typeBlood;
  private Spinner cardiac;
  private Spinner diabect;
  private Spinner hypertension;
  private Spinner seropositive;

  private Button saveButton;
  private Button updateButton;
  private Button deleteButton;

  private String nameUser = "";
  private String birthdayUser = "";
  private String typeBloodUser = "";
  private String cardiacUser = "";
  private String observationsUser = "";
  private String diabeticUser = "";
  private String hypertensionUser = "";
  private String seropositiveUser = "";

  private static final String TITLE_MESSAGE = "Ficha Médica";
  private static final String TEXT_MESSAGE = "Você tem uma ficha médica!";
  private static final String ALERT_MESSAGE = "Alerta de Mensagem";
  private static final String FUNCTION_NOT_ENABLE_MESSAGE = "Função não habilitada!";
  private static final String EMPTY_NAME_MESSAGE = "Nome Vazio! Informe Seu Nome.";
  private static final String LITTLE_NAME_MESSAGE = "Informe um nome com no mínimo 3 caracteres.";
  private static final String YEAR_OLD_MESSAGE = "Informe um ano superior a 1942.";
  private static final String INVALID_YEAR_MESSAGE = "Ops, essa data é inválida!.";
  private static final String INVALID_DATE_MESSAGE = "Data Inválida! Informe uma data inválida," +
          "com o dia entre 1 e 31.\n";
  private static final String REQUEST_MONTH_MESSAGE = "Informe um mês válido entre 1 e 12.\n";
  private static final String REQUEST_YEAR_MESSAGE = "Informe um ano entre 1942 e o ano atual";
  private static final String BIRTH_DATE_MESSAGE = "Informe a sua data de nascimento.";
  private static final String CHANGE_MESSAGE = "Alteração Realizada Com Sucesso!";
  private static final String CHANGE_NOT_DONE = "Não Foi Possível Fazer A Alteração, Tente " +
          "Novamente.";
  private static final String REQUEST_OF_EXCLUSION = "Deseja Mesmo Excluir Esta Ficha Médica?";
  private static final String TITLE_REQUEST_OF_EXCLUSION = "Deseja Mesmo Excluir Esta Ficha " +
          "Médica?";
  private static final String EXCLUSION_MESSAGE = "Ficha Médica Excluida Com Sucesso";
  private static final String MEDICAL_FORM_REGISTERED = "Ficha Médica Cadastrada Com Sucesso!";
  private static final String MEDICAL_FORM_NOT_REGISTERED = "Ficha Médica Não Cadastrada! Tente " +
          "Novamente.";
  private Integer identifier = 1;
  UserDao myDatabase;
  private static final int MAXIMUM_ARRAY = 7;

  public MedicalRecordsController() {

  }

  @Override
    protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.medical_records);

    myDatabase = new UserDao(this);

    fullName = (EditText) findViewById(R.id.fullNameEditText);
    birthday = (EditText) findViewById(R.id.birthdayEditText);
    observations = (EditText) findViewById(R.id.observationsEditText);
    typeBlood = (Spinner) findViewById(R.id.typeBloodSpinner);
    birthday.addTextChangedListener(MaskHelper.insert("##/##/####", birthday));
    cardiac = (Spinner) findViewById(R.id.cardiacSpinner);
    diabect = (Spinner) findViewById(R.id.diabeticSpinner);
    hypertension = (Spinner) findViewById(R.id.hipertensionSpinner);
    seropositive = (Spinner) findViewById(R.id.soropositiveSpinner);
    saveButton = (Button) findViewById(R.id.saveButton);
    updateButton = (Button) findViewById(R.id.updateButton);
    deleteButton = (Button) findViewById(R.id.deleteButton);

    Cursor result = myDatabase.getUser();

    if (result.getCount() == 0) {
      disableOptions(saveButton,updateButton,deleteButton);
    } else {
      if (result.moveToFirst()) {
        fullName.setText(result.getString(1));
        birthday.setText(result.getString(2));
        observations.setText(result.getString(8));
        disableField(saveButton,fullName,birthday,observations,cardiac,diabect,hypertension,
                        seropositive,typeBlood);
      }
    }
    saveButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
            if (createUser() == false) {
              saveButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                      createUser();
                    }
              });
            } else {
              disableButtons(saveButton,updateButton,deleteButton);
            }
          }
        });

    updateButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        cancelNotification();
        disableJustUpdateButton(fullName, birthday,updateButton,saveButton,observations,
                        typeBlood,cardiac,diabect,hypertension,seropositive);
        saveButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
            updateUser(identifier,saveButton,updateButton,deleteButton);
            visibleOptions(saveButton,updateButton);
          }
        });
      }
    });

    deleteButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
          deleteUser(fullName,birthday,observations,saveButton,identifier,updateButton,deleteButton,
                        typeBlood,cardiac,diabect,hypertension,seropositive);
          saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
              if (createUser() == false) {
                saveButton.setOnClickListener(new View.OnClickListener() {
                  public void onClick(View view) {
                        createUser();
                  }
                });
              } else {
                    disableButtons(saveButton,updateButton,deleteButton);
              }
            }
          });
      }
        });
  }


  private boolean createUser() {
    boolean sucess = true;
    boolean valid = false;

    if (checksName(fullName.getText().toString()) == false
                && checkBirthday(birthday.getText().toString()) == false) {

      birthdayUser = birthday.getText().toString();
      observationsUser = observations.getText().toString();
      nameUser = fullName.getText().toString();
      typeBloodUser = typeBlood.getSelectedItem().toString();
      cardiacUser = cardiac.getSelectedItem().toString();
      diabeticUser = diabect.getSelectedItem().toString();
      hypertensionUser = diabect.getSelectedItem().toString();
      seropositiveUser = seropositive.getSelectedItem().toString();

      sucess = myDatabase.insertUser(identifier, nameUser, birthdayUser, typeBloodUser, cardiacUser,
                    diabeticUser,hypertensionUser, seropositiveUser,
                    observationsUser);
      if (sucess == true) {
        showMessage(MEDICAL_FORM_REGISTERED);
        disableOptionsCreateUser(fullName,birthday,observations,typeBlood,cardiac,diabect,
                        hypertension,seropositive);
        disableOptionsUpdate(saveButton,updateButton,deleteButton);
        medicalRecordsNotification(nameUser,birthdayUser,typeBloodUser,cardiacUser,
                                            diabeticUser,hypertensionUser,seropositiveUser,
                                            observationsUser);
        valid = true;
      } else {
        showMessage(MEDICAL_FORM_NOT_REGISTERED);
        valid = false;
      }
    }
    return valid;
  }

  private void updateUser(Integer id,Button save,Button update,
                            Button delete) {

    boolean sucess = true;

    if (checksName(fullName.getText().toString()) == false
                && checkBirthday(birthday.getText().toString()) == false) {

      nameUser = fullName.getText().toString();
      birthdayUser = birthday.getText().toString();
      observationsUser = observations.getText().toString();
      nameUser = fullName.getText().toString();
      typeBloodUser = typeBlood.getSelectedItem().toString();
      cardiacUser = cardiac.getSelectedItem().toString();
      diabeticUser = diabect.getSelectedItem().toString();
      hypertensionUser = diabect.getSelectedItem().toString();
      seropositiveUser = seropositive.getSelectedItem().toString();

      sucess = myDatabase.updateUser(id, nameUser, birthdayUser, typeBloodUser, cardiacUser,
              diabeticUser, hypertensionUser, seropositiveUser,observationsUser);
      if (sucess == true) {
        showMessage(CHANGE_MESSAGE);
        save.setVisibility(View.VISIBLE);
        disableOptionsCreateUser(fullName,birthday,observations,typeBlood,cardiac,diabect,
                       hypertension,seropositive);
        save.setEnabled(false);
        update.setEnabled(true);
        delete.setEnabled(true);
        medicalRecordsNotification(nameUser,birthdayUser,typeBloodUser,cardiacUser,
                                            diabeticUser,hypertensionUser,seropositiveUser,
                                            observationsUser);
      } else {
        showMessage(CHANGE_NOT_DONE);
      }
    }
  }

  private void deleteUser(final EditText name, final EditText birthday,
                            final EditText observations,
                            final Button save, final Integer id, final Button update,
                            final Button delete,final Spinner typeBlood,final Spinner cardiac,
                            final Spinner diabect,final Spinner hypertension,
                            final Spinner seropositive) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle(REQUEST_OF_EXCLUSION);
    builder.setMessage(TITLE_REQUEST_OF_EXCLUSION);
    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
        @Override
            public void onClick(DialogInterface dialog, int which) {
            myDatabase.deleteUser(id);
            showMessage(EXCLUSION_MESSAGE);
            visibleOptionsUser(save,name,birthday,observations,update,delete,typeBlood,cardiac,
                        hypertension,seropositive,diabect);
            cancelNotification();
          }
        });
    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
          @Override
            public void onClick(DialogInterface dialog, int which) {

          }
        });
    builder.show();
  }

  private void showMessage(String message) {
    Toast.makeText(this,"" + message,Toast.LENGTH_LONG).show();
  }

  private boolean checksName(String nameUser) {
    final int minimumSizeName = 3;
    if (nameUser.isEmpty()) {
      showMessage(EMPTY_NAME_MESSAGE);
      return true;
    }
    if (nameUser.trim().length() < minimumSizeName) {
      showMessage(LITTLE_NAME_MESSAGE);
      fullName.requestFocus();
      return true;
    }
    return false;
  }

  private boolean checkBirthday(String birthdayUser) {
    final int minimumYear = 42;

    if (!birthdayUser.isEmpty() && birthdayUser != null) {
      try {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        Date userDate = format.parse(birthdayUser);

        if (userDate.before(new Date())) {
          if (userDate.getYear() < minimumYear) {
            showMessage(YEAR_OLD_MESSAGE);
            return true;
          }
          this.birthdayUser = birthdayUser;
          return false;
        } else {
          showMessage(INVALID_YEAR_MESSAGE);
          return true;
        }
      } catch (ParseException excecao) {
        showMessage(INVALID_DATE_MESSAGE + REQUEST_MONTH_MESSAGE + REQUEST_YEAR_MESSAGE);
        return true;
      }
    } else {
      showMessage(BIRTH_DATE_MESSAGE);
      return true;
    }
  }

  private void visibleOptionsUser(Button save,EditText name,EditText birthday,
                                    EditText observations,Button update,Button delete,
                                    Spinner typeBlood,Spinner cardiac,Spinner hypertension,
                                    Spinner seropositive,Spinner diabect) {
    save.setVisibility(View.VISIBLE);
    save.setEnabled(true);
    name.setText("");
    birthday.setText("");
    observations.setText("");
    diabect.setEnabled(true);
    name.setEnabled(true);
    birthday.setEnabled(true);
    observations.setEnabled(true);
    typeBlood.setEnabled(true);
    cardiac.setEnabled(true);
    hypertension.setEnabled(true);
    seropositive.setEnabled(true);
    update.setVisibility(View.INVISIBLE);
    delete.setVisibility(View.INVISIBLE);
  }

  private void disableOptions(Button save, Button update,Button delete) {
    save.setVisibility(View.VISIBLE);
    update.setVisibility(View.INVISIBLE);
    delete.setVisibility(View.INVISIBLE);
  }

  private void cancelNotification() {
    NotificationManager notifManager = (NotificationManager) this.getSystemService(Context
             .NOTIFICATION_SERVICE);
    notifManager.cancel(1);
  }

  private void disableField(Button save,EditText name, EditText birthday,EditText observations,
                              Spinner cardiac,Spinner diabect,Spinner hypertension,
                              Spinner seropositive,Spinner typeBlood) {
    save.setVisibility(View.INVISIBLE);
    name.setEnabled(false);
    birthday.setEnabled(false);
    observations.setEnabled(false);
    cardiac.setEnabled(false);
    typeBlood.setEnabled(false);
    diabect.setEnabled(false);
    hypertension.setEnabled(false);
    seropositive.setEnabled(false);
  }

  private void disableOptionsUpdate(Button save,Button update,Button delete) {
    save.setEnabled(false);
    update.setVisibility(View.VISIBLE);
    update.setEnabled(true);
    delete.setVisibility(View.VISIBLE);
    delete.setEnabled(true);
  }

  private void disableButtons(Button save,Button update,Button delete) {
    save.setVisibility(View.INVISIBLE);
    update.setVisibility(View.VISIBLE);
    update.setEnabled(true);
    delete.setVisibility(View.VISIBLE);
    delete.setEnabled(true);
  }

  private void disableJustUpdateButton(EditText name, EditText birthday, Button update, Button save,
                                         EditText observations,Spinner typeBlood,Spinner cardiac,
                                         Spinner diabect,Spinner hypertension,Spinner
                                                 seropositive) {
    name.setEnabled(true);
    birthday.setEnabled(true);
    observations.setEnabled(true);
    update.setVisibility(View.INVISIBLE);
    save.setVisibility(View.VISIBLE);
    save.setEnabled(true);
    typeBlood.setEnabled(true);
    cardiac.setEnabled(true);
    diabect.setEnabled(true);
    hypertension.setEnabled(true);
    seropositive.setEnabled(true);
  }

  private void visibleOptions(Button save,Button update) {
    update.setVisibility(View.VISIBLE);
    save.setVisibility(View.INVISIBLE);
  }

  private void disableOptionsCreateUser(EditText name,EditText birthday,EditText observations,
                                          Spinner typeBlood,Spinner cardiac,Spinner diabect,
                                          Spinner hypertension,Spinner seropositive) {
    name.setEnabled(false);
    birthday.setEnabled(false);
    observations.setEnabled(false);
    typeBlood.setEnabled(false);
    cardiac.setEnabled(false);
    diabect.setEnabled(false);
    hypertension.setEnabled(false);
    seropositive.setEnabled(false);
  }

  public void medicalRecordsNotification(String nameUser,String birthdayUser,String typeBloodUser,
                                         String cardiacUser,String diabeticUser,
                                         String hypertensionUser,String seropositiveUser,
                                         String observationsUser) {
    final int notifyIdentifier = 1;
    NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

    notification.setContentTitle(TITLE_MESSAGE);
    notification.setContentText(TEXT_MESSAGE);
    notification.setTicker(ALERT_MESSAGE);
    notification.setSmallIcon(R.drawable.icon_emergo);

    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

    String events[] = new String[MAXIMUM_ARRAY];

    events[0] = new String("Nome: "  + nameUser);
    events[1] = new String("Data de Nascimento: " + birthdayUser);
    events[2] = new String("Tipo Sanguineo: " + typeBloodUser);
    events[3] = new String("Cardiaco: " + cardiacUser);
    events[4] = new String("Diabetico: " + diabeticUser);
    events[5] = new String("Hipertenso: " + hypertensionUser);
    events[6] = new String("Soropositivo: " + seropositiveUser);
    events[6] = new String("Observações Especiais: " + observationsUser);

    inboxStyle.setBigContentTitle(TITLE_MESSAGE);

    for (int aux = 0;aux < MAXIMUM_ARRAY;aux++) {
      inboxStyle.addLine(events[aux]);
    }
    notification.setStyle(inboxStyle);

    Intent resultIntent = new Intent(this,MedicalRecordsController.class);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    stackBuilder.addParentStack(MedicalRecordsController.class);
    stackBuilder.addNextIntent(resultIntent);
    PendingIntent resultPedindIntent = stackBuilder.getPendingIntent(0,
            PendingIntent.FLAG_UPDATE_CURRENT);
    notification.setContentIntent(resultPedindIntent);

    NotificationManager notificationManager = (NotificationManager)
            getSystemService(NOTIFICATION_SERVICE);

    notificationManager.notify(notifyIdentifier,notification.build());
  }

  public void goClicked(View mapScreen) {
    Toast.makeText(this , FUNCTION_NOT_ENABLE_MESSAGE , Toast.LENGTH_SHORT).show();
    Intent routeActivity = new Intent();
    routeActivity.setClass(this, RouteActivity.class);
    startActivity(routeActivity);
    finish();
  }

  public void listMapsImageClicked(View mapScreen) {
    Intent listOfHealth = new Intent();
    listOfHealth.setClass(this , ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
  }

  public void openConfig(View map_screen) {
    Intent config = new Intent();
    config.setClass(this , ConfigController.class);
    startActivity(config);
    finish();
  }

  public void openMap(View mapScreen) {
    Intent mapActivity = new Intent();
    mapActivity.setClass(this, MapScreenController.class);
    startActivity(mapActivity);
    finish();
  }

  public void openSearch(View mapScreen) {
    Intent openSearch = new Intent();
    openSearch.setClass(this , SearchHealthUnitController.class);
    startActivity(openSearch);
  }
}