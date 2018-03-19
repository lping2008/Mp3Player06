package Fragment;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.pl.mp3player06.R;

import Activity.MainActivity;

/**
 * 左侧菜单Fragment
 */
public class LeftMenuFragment extends BaseFragment {

	private static final String TAG = LeftMenuFragment.class.getSimpleName();
//	ArrayList<NewsMenuBean> mMenuList;// 菜单列表
	
	private ListView lvList;
	public TextView tvTitle;
	
	private LeftMenuAdapter mAdapter;

	private int mCurrentPosition;// 当前选中第几个菜单
	private ArrayList<String> mMenuList;
	String mMenuLists[]= {"乐库","本地","推荐","关于软件"};
	
	@Override
	public void initData() {
		super.initData();
		mMenuList = new ArrayList<String>();
		for(int i =0;i<4;i++){
			mMenuList.add(mMenuLists[i]);
		}
		setLeftMenuData(mMenuList);
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		lvList =(ListView) view.findViewById(R.id.lv_left_menu);
		
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPosition = position;
				mAdapter.notifyDataSetChanged();// 刷新listview

				MainActivity mainUI = (MainActivity) mActivity;
				SlidingMenu slidingMenu = mainUI.getSlidingMenu();
				slidingMenu.toggle();// 如果侧边栏打开,则关闭;如果关闭,则打开
				
				setCurrentDetailPager(position);//设置当前要显示的详情页面
				mCurrentPosition = 0;
				mAdapter.notifyDataSetChanged();
			}
		});

		return view;
	}

	/**
	 * 设置当前要显示的页面
	 * @param position
	 */
	protected void setCurrentDetailPager(int position) {
		MainActivity mainUI = (MainActivity) mActivity;
//		ContentFragment fragment = mainUI.getContentFragment();//获取主页Fragment
//		fragment.setCurrentPager(position);
	}

	/**
	 * 设置侧边栏数据
	 * 
	 * @param data
	 */
	public void setLeftMenuData(ArrayList<String> data) {
		this.mMenuList = data;
		mCurrentPosition = 0;//初始化位置信息
		mAdapter = new LeftMenuAdapter();
		lvList.setAdapter(mAdapter);//设置listview的数据源
	}

	class LeftMenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mMenuList.size();
		}

		@Override
		public String getItem(int position) {
			return mMenuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.left_menu_list_item,
					null);
			tvTitle = (TextView) view.findViewById(R.id.left_menu_title);
			tvTitle.setText(getItem(position));

			// 如果是当前选中的item, 置为可用,显示为红色,如果不是,置为不可用,显示为白色
			tvTitle.setEnabled(position == mCurrentPosition);
			return view;
		}
	}
}
