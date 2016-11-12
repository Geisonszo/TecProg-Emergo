/********************
 * Class name: SearchHealthUnitController (.java)
 *
 * Purpose: The purpose of this class is to search the closest health units.
 ********************/

package unlv.erc.emergo.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import unlv.erc.emergo.R;
import unlv.erc.emergo.model.HealthUnit;

import java.util.ArrayList;
import java.util.List;



public class SearchHealthUnitController extends AppCompatActivity implements SearchView
    .OnQueryTextListener, View.OnClickListener {

  private SearchView mapSearchView;
  private int numberOfHealthUnitClicked = 0; //Index responsable for future searching methods
  private ListView healthUnitsList;
  ArrayList<String> closestHealthUnit;
  ArrayList<HealthUnit> healthUnit;

  public ListView getHealtshUnitList() {

    return healthUnitsList;
  }

  public SearchView getMapSearchView() {

    return mapSearchView;
  }

  public void setMapSearchView(SearchView mapSearchView) {

    assert mapSearchView != null : "mapSearchView can't be null";

    this.mapSearchView = mapSearchView;
  }

  /**
   * This method open the screen with inforamtion about the health unit.
   */
  public void openInformationHealthUnitScreen() {

    Intent informationScreen = new Intent();

    informationScreen.putExtra("position", numberOfHealthUnitClicked);
    informationScreen.setClass(SearchHealthUnitController.this, InformationSearchScreenController.class);
    startActivity(informationScreen);
    finish();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    super.onCreateOptionsMenu(menu);

    ImageView goButton = (ImageView) findViewById(R.id.buttonGo);
    goButton.setOnClickListener(this);

    ImageView healthUnitList = (ImageView) findViewById(R.id.iconList);
    healthUnitList.setOnClickListener(this);

    ImageView map = (ImageView) findViewById(R.id.iconMap);
    map.setOnClickListener(this);

    MenuInflater inflater = getMenuInflater();

    inflater.inflate(R.menu.menu_search, menu);
    mapSearchView = (SearchView) menu.findItem(R.id.search)
      .getActionView();
    mapSearchView.setQueryHint("Busca");
    mapSearchView.setOnQueryTextListener(this);

    return true;
  }

  @Override
  public void onClick(View searchView) {
    if (searchView.getId() == R.id.buttonGo) {

      Intent route = new Intent();

      route.setClass(SearchHealthUnitController.this, RouteActivity.class);
      startActivity(route);
      finish();
    }
    if (searchView.getId() == R.id.iconMap) {

      Intent map = new Intent();

      map.setClass(SearchHealthUnitController.this, MapScreenController.class);
      map.putExtra("numeroUs" , -1);
      startActivity(map);
      finish();
    }
    if (searchView.getId() == R.id.iconList) {

      Intent list = new Intent();

      list.setClass(SearchHealthUnitController.this, ListOfHealthUnitsController.class);
      startActivity(list);
      finish();
    }
  }

  /*
   * This method search the health unit on map.
   * @param closest the closest health unit
   * @return the closest health unit
   */

  private ArrayList<String> getSearchHealthUnit(ArrayList<HealthUnit> closest) {

   ArrayList<String> searchArray = new ArrayList<>();

    try {

      CharSequence search = mapSearchView.getQuery();
      closestHealthUnit = new ArrayList<>();
      healthUnit = new ArrayList<>();
      int numberOfUs;

        for (numberOfUs = 0; numberOfUs < HealthUnitController.getClosestHealthUnit().size();
            numberOfUs++) {
          if (closest.get(numberOfUs).getNameHospital().toLowerCase().contains(search)) {
            closestHealthUnit.add(closest.get(numberOfUs).getNameHospital());
            healthUnit.add(HealthUnitController.getClosestHealthUnit().get(numberOfUs));
        }
      }
      return closestHealthUnit;
    }catch (NullPointerException exception){
      exception.printStackTrace();
    }

    return searchArray;
  }

  public void setHealthUnitsList(ListView healthUnitsList) {
    try {
      this.healthUnitsList = healthUnitsList;
    }catch(NullPointerException exception){
      exception.printStackTrace();
    }
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {

    List<String> searchHealthUnit = getSearchHealthUnit(HealthUnitController.getClosestHealthUnit());

    setHealthUnitsList((ListView) findViewById(R.id.list_of_search_us));
    healthUnitsList.setAdapter(new ArrayAdapter<>(this, R.layout.item,
        R.id.hospitalUnitText, searchHealthUnit));
    healthUnitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        numberOfHealthUnitClicked = parent.getPositionForView(view);
        openInformationHealthUnitScreen();
      }
    });
    return false;
  }

  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_screen);
  }
}