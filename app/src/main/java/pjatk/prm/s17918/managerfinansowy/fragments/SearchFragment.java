package pjatk.prm.s17918.managerfinansowy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import pjatk.prm.s17918.managerfinansowy.MainActivity;
import pjatk.prm.s17918.managerfinansowy.R;
import pjatk.prm.s17918.managerfinansowy.adapters.SectionsPageAdapter;

public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.null_string);
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(new SearchByName(), getResources().getString(R.string.name));
        adapter.addFragment(new SearchByCategory(), getResources().getString(R.string.category));
        adapter.addFragment(new SearchByDate(), getResources().getString(R.string.date));
        viewPager.setAdapter(adapter);
    }
}