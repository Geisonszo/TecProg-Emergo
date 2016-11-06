/************************
 * Class name: InformationHealthUnitScreenController (.java)
 *
 * Purpose: The purpose of this class is to show the information of the healthunits.
 ************************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unlv.erc.emergo.R;
import unlv.erc.emergo.model.HealthUnit;

public class InformationHealthUnitScreenController extends Activity
    implements View.OnClickListener {

  // The value to be returned if no value of the desired type is stored with the given name.
  private static final int RETURN_NOT_FOUND = 0;
  // The Bundle data value of numeroUs .
  private static final int VALUE_LOWER_CLOSE = -1;
  // The name of the desired item.
  private static final String POSITION = "position";
  // The Bundle data value.
  private static final String NUMBER_HEALTH_UNIT = "numeroUs";
  private List<String> listOfInformations = new ArrayList<String>();
  private ListView healthUnitInfo;
  private Button buttonRoute;
  private ImageView buttonGo;
  private Intent receive;
  private int numberHealthUnitSelected = 0;
  private String padding = "";
  private String titleHealthUnit = "";
  private String nameHealthUnit = "";
  private String healthUnitType = "";
  private String state = "";
  private String city = "";
  private String district = "";
  private String addressNumber = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.information_us_screen);

    setReceive(getIntent());
    setSearchHelthUnitSelected(receive.getIntExtra(POSITION , RETURN_NOT_FOUND));
    buttonRoute = (Button) findViewById(R.id.botaoRota);
    buttonRoute.setOnClickListener(this);
    buttonGo = (ImageView) findViewById(R.id.buttonGo);
    buttonGo.setOnClickListener(this);

    setHealthUnitInfo((ListView) findViewById(R.id.hospInformation));
    setInformation(HealthUnitController.getClosestHealthUnit().get(numberHealthUnitSelected));
    addInformationToList();
  }

  /**
   * The purpose of this function is to be in accordance with the choice of User buttons, to open
   * the RouteActivity class but with two options:
   * If he hit the "buttonRoute" to open the map showing the nearest health facilities.
   * If he hit the "buttonGo" to open the map tracing the route to the nearest health facility,
   * and call the samu and send a message pros emergency contacts.
   * @param viewOnClick viewOnClick.
   *
   */

  @Override
  public void onClick(View viewOnClick) {

    /**
     * When the "buttonRoute" is pressed, it will exit the current class and opened the
     * activity and "RouteActivity" class. When RouteActivity is finished, come in method
     * "finish ()" which will close the activity "RouteActivity".
     *
     */

    if (viewOnClick.getId() == R.id.botaoRota) {

      Intent route = new Intent();

      route.setClass(InformationHealthUnitScreenController.this , RouteActivity.class);
      route.putExtra(NUMBER_HEALTH_UNIT , receive.getIntExtra(POSITION , RETURN_NOT_FOUND));
      startActivity(route);
      finish();
    } else {

      //Nothing to do.
    }

    /**
     * When the "buttonGo" is pressed, it will exit the current class, opened the activity
     * "RouteActivity," only different from the method of "buttonRoute", will now be chosen the
     * route nearest the user.When the RouteActivity is finished, come in method "finish ()"
     * which will close the activity "RouteActivity".
     *
     */

    if (viewOnClick.getId() == R.id.buttonGo) {

      String routeTraced = "Rota mais próxima traçada";
      Intent routeActivity = new Intent();

      Toast.makeText(this, routeTraced , Toast.LENGTH_SHORT).show();
      routeActivity.setClass(InformationHealthUnitScreenController.this , RouteActivity.class);
      routeActivity.putExtra(NUMBER_HEALTH_UNIT , VALUE_LOWER_CLOSE);
      startActivity(routeActivity);
      finish();
    } else {

      //Nothing to do.
    }
  }

  /**
   * Get the value of attribute titleHealthUnit.
   * @return titleHealthUnit: String.
   *
   */

  public String getTitleHealthUnit() {

    return titleHealthUnit;
  }

  private Intent getReceive() {

    return receive;
  }

  /**
    * Get the value of attribute of image buttonGo.
    * @return buttonGo: ImageView.
    *
  */

  public ImageView getButtonGo() {

    return buttonGo;
  }

  /**
    * Get the value of attribute padding.
    * @return padding: String.
    *
   */

  public String getPadding() {

    return padding;
  }

  /**
   * Get the value of attribute nameHealthUnit.
   * @return nameHealthUnit: String.
   *
   */

  public String getNameHealthUnit() {

    return nameHealthUnit;
  }

  /**
    * Get the value of attribute healthUnitType.
    * @return healthUnitType: String.
    *
   */

  public String getUnitType() {

    return healthUnitType;
  }

  /**
    * Get the value of attribute state.
    * @return state: String.
    *
   */

  public String getState() {

    return state;
  }

  /**
    * Get the value of attribute city.
    * @return city: String.
    *
   */

  public String getCity() {

    return city;
  }

  /**
   * Get the value of attribute district.
   * @return district: String.
   *
   */

  public String getDistrict() {

    return district;
  }

  /**
    * Get the value of attribute addressNumber.
    * @return addressNumber: String.
    *
   */

  public String getAddressNumber() {

    return addressNumber;
  }

  /**
   * Directs you to the Settings screen.
   * @param mapScreen View.
   */

  public void openSettings(View mapScreen) {
    Intent config = new Intent();
    config.setClass(this , ConfigController.class);
    startActivity(config);
    finish();
  }

  /**
   * Exchange of current activity and start activity "SearchController".
   * @param mapScreen view mapScreen.
   *
   */

  public void openSearch(View mapScreen) {

    Intent openSearch = new Intent();

    openSearch.setClass(this,SearchHealthUnitController.class);
    startActivity(openSearch);
  }

  /**
   * Exchange of current activity and start activity "ListOfHealthUnitsController".
   * @param mapScreen View mapScreen.
   *
   */

  public void listMapsImageClicked(View mapScreen) {

    Intent listOfHealth = new Intent();

    listOfHealth.setClass(this,ListOfHealthUnitsController.class);
    startActivity(listOfHealth);
    finish();
  }

  /**
   * Exchange of current activity and start activity "MapScreenController".
   * @param mapScreen View mapScreen.
   *
   */

  public void openMap(View mapScreen) {

    Intent mapActivity = new Intent();

    mapActivity.setClass(this,MapScreenController.class);
    startActivity(mapActivity);
    finish();
  }

  /**
   * Get the value of attribute listOfInformations.
   * @return listofInformations: String.
   *
   */

  private List<String> getListOfInformations() {

    return listOfInformations;
  }

  /**
   * Get the value of attribute healthUnitInfo.
   * @return healthUnitInfo: ListView
   *
   */

  private ListView getHealthUnitInfo() {

    return healthUnitInfo;
  }

  /**
   * Get the value of attribute buttonRoute.
   * @return buttonRoute: Button.
   *
   */

  private Button getButtonRoute() {

    return buttonRoute;
  }

  /**
   * Get the value of attribute numberHealthUnitSelected.
   * @return numberHealthUnitSelected: int.
   *
   */

  private int getNumberUsSelected() {

    return numberHealthUnitSelected;
  }

  /**
   * Takes the arraylist data that is linked to the related database healthUnits.
   * @param healthUnit HealthUnit healthUnit.
   *
   */

  private void setInformation(HealthUnit healthUnit) {

    setPadding("\n");
    setTitleHealthUnit("        Informações da Unidade de Saúde");
    setNameHealthUnit("  Nome: " + healthUnit.getNameHospital());
    setUnitType("  Tipo de atendimento: " + healthUnit.getUnitType());
    setState("  UF: " + healthUnit.getState());
    setCity("  Cidade: " + healthUnit.getCity());
    setDistrict("  Bairro: " + healthUnit.getDistrict());
    setAddressNumber("  Cep: " + healthUnit.getAddressNumber());
  }

  /**
   * Takes the data from the database, saved in a arraylist and calls another function, where the
   * saved data in ArrayLists will be shown in a ArrayAdapter.
   *
   */

  private void addInformationToList() {

    listOfInformations.add(padding);
    listOfInformations.add(titleHealthUnit);
    listOfInformations.add(nameHealthUnit);
    listOfInformations.add(healthUnitType);
    listOfInformations.add(state);
    listOfInformations.add(city);
    listOfInformations.add(district);
    listOfInformations.add(addressNumber);
    showInformationOnScreen();
  }

  /**
   * Shows the information saved in the arraylist and put in a ArrayAdapter.
   *
   */

  private void showInformationOnScreen() {

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            listOfInformations);

    healthUnitInfo.setAdapter(adapter);
  }

  /**
   * Set the value of attribute listOfInformations.
   * @param listOfInformations List.
   *
   */

  private void setListOfInformations(List<String> listOfInformations) {

    this.listOfInformations = listOfInformations;
  }

  /**
   * Set the value of attribute healthUnitInfo.
   * @param healthUnitInfo ListView.
   *
   */

  private void setHealthUnitInfo(ListView healthUnitInfo) {

    this.healthUnitInfo = healthUnitInfo;
  }

  /**
   * Set the value of attribute receive.
   * @param receive Intent.
   *
   */

  private void setReceive(Intent receive) {

    this.receive = receive;
  }

  /**
   * Set the value of attribute buttonRoute.
   * @param buttonRoute Button.
   *
   */

  private void setButtonRoute(Button buttonRoute) {

    this.buttonRoute = buttonRoute;
  }

  /**
   * Set the value of attribute of image buttonGo.
   * @param buttonGo ImageView.
   *
   */

  private void setButtonGo(ImageView buttonGo) {

    this.buttonGo = buttonGo;
  }

  /**
   * Set the value of attribute numberHealthUnitSelected.
   * @param numberHealthUnitSelected int.
   */

  private void setSearchHelthUnitSelected(int numberHealthUnitSelected) {

    this.numberHealthUnitSelected = numberHealthUnitSelected;
  }

  /**
   * Get the value of attribute receive.
   * @return healthUnitInfo: Intent.
   *
   */

  /**
   * Set the value of attribute padding.
   * @param padding String.
   *
   */

  private void setPadding(String padding) {

    this.padding = padding;
  }

  /**
   * Set the value of attribute titleHealthUnit.
   * @param titleHealthUnit String.
   *
   */

  private void setTitleHealthUnit(String titleHealthUnit) {

    this.titleHealthUnit = titleHealthUnit;
  }

  /**
   * Set the value of attribute nameHealthUnit.
   * @param nameHealthUnit String.
   *
   */

  private void setNameHealthUnit(String nameHealthUnit) {

    this.nameHealthUnit = nameHealthUnit;
  }

  /**
   * Set the value of attribute healthUnitType.
   * @param healthUnitType String.
   *
   */

  private void setUnitType(String healthUnitType) {

    this.healthUnitType = healthUnitType;
  }

  /**
   * Set the value of attribute state.
   * @param state String.
   *
   */

  private void setState(String state) {

    this.state = state;
  }

  /**
   * Set the value of attribute city.
   * @param city String.
   *
   */

  private void setCity(String city) {

    this.city = city;
  }

  /**
   * Set the value of attribute district.
   * @param district String.
   *
   */

  private void setDistrict(String district) {

    this.district = district;
  }

  /**
   * Set the value of attribute addressNumber.
   * @param addressNumber String.
   *
   */

  private void setAddressNumber(String addressNumber) {

    this.addressNumber = addressNumber;
  }
}


