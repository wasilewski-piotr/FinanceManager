package pjatk.prm.s17918.managerfinansowy;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import pjatk.prm.s17918.managerfinansowy.transporters.MonthTransporter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("LLLL", Locale.forLanguageTag("PL"));
        String month = month_date.format(cal.getTime());
        String month_name = month.substring(0, 1).toUpperCase() + month.substring(1);
        MonthTransporter.setMonth(month_name);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.main_page: {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.main, true)
                        .build();
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.events_fragment, null, navOptions);
                break;
            }

            case R.id.add_here:{
                if(isValidDestination(R.id.add_here)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.add_fragment);
                }
                break;
            }

            case R.id.search:{
                if(isValidDestination(R.id.search)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.search_fragment);
                }
                break;
            }

            case R.id.expenses:{
                if(isValidDestination(R.id.expenses)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.expenses_fragment);
                }
                break;
            }

            case R.id.incomes:{
                if(isValidDestination(R.id.incomes)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.income_fragment);
                }
                break;
            }

            case R.id.monthStats:{
                if(isValidDestination(R.id.monthStats)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.month_stats);
                }
                break;
            }
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:{
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }else{
                    return false;
                }
            }
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void init(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private boolean isValidDestination(int dest){
        return dest != Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }
}