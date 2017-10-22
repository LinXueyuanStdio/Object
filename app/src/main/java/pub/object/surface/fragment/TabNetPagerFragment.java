package pub.object.surface.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pub.object.R;
import pub.object.bone.base.BaseFragment;
import pub.object.bone.interfaces.ChangeView;
import pub.object.system.ThemeSystem.utils.ThemeUtils;

/**
 * Created by wm on 2016/4/11.
 */
public class TabNetPagerFragment extends BaseFragment implements ChangeView {
    private static final String TAG = "TabNetPagerFragment";

    private ViewPager viewPager;
    private int pageIndex = 0;
    private ActionBar ab;
    private String[] title;
    private boolean isFirstLoad = true;

    public static final TabNetPagerFragment newInstance(int page, String[] title) {
        TabNetPagerFragment f = new TabNetPagerFragment();
        Bundle bdl = new Bundle(1);
        bdl.putInt("page_number", page);
        f.setArguments(bdl);
        return f;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setCurrentItem(pageIndex);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_net_tab, container, false);


        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(2);
        }

        final TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setTabTextColors(R.color.text_black, ThemeUtils.getThemeColorStateList(mContext, R.color.theme_color_primary).getDefaultColor());
        tabLayout.setSelectedTabIndicatorColor(ThemeUtils.getThemeColorStateList(mContext, R.color.theme_color_primary).getDefaultColor());
        tabLayout.setupWithViewPager(viewPager);

        return rootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if(recommendFragment == null){
//            return;
//        }
//        if(isVisibleToUser && isFirstLoad){
//            recommendFragment.requestData();
//            isFirstLoad = false;
//        }
    }

    HelpFragment helpFragment;
    GetHelpFragment getHelpFragment;
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());

        helpFragment = new HelpFragment();
        getHelpFragment = new GetHelpFragment();

        adapter.addFragment(helpFragment, "帮");
        adapter.addFragment(getHelpFragment, "派");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void changeTo(int page) {
        if (viewPager != null)
            viewPager.setCurrentItem(page);
    }

    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}

