package com.github.fabactivitytransition.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.fabactivitytransition.FabActivityTransition;
import com.github.fabactivitytransition.demo.model.Mail;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

	private static final int REQUEST_CODE = 1;
	@Bind(R.id.fab) FloatingActionButton mFab;
    @Bind(R.id.list_mails) ListView listMails;
    ArrayList<Mail> mailList = new ArrayList<>();
	private FabActivityTransition fabActivityTransition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolbarWithTitle(getString(R.string.INBOX), false);
		fabActivityTransition = new FabActivityTransition.Builder(this)
				.withFab(mFab)
				.withStartActivityCallback(new FabActivityTransition.StartActivityCallback() {
					@Override
					public void onNeedToStartActivity() {
						Intent intent = new Intent(MainActivity.this, AfterFabAnimationActivity.class);
						startActivityForResult(intent, REQUEST_CODE);
					}
				})
				.build();
		mFab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fabActivityTransition.start();
			}
		});
		fillMailList();
        listMails.setAdapter(new MailAdapter());
    }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if(requestCode == REQUEST_CODE){
		   fabActivityTransition.reverse();
	   }
   }


    private void fillMailList(){
        String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";

        mailList.add(new Mail(1, "Abbrey Christensen", message, "Nov 5"));
        mailList.add(new Mail(2, "Alex Nelson", message, "Nov 5"));
        mailList.add(new Mail(3, "Mary Johnson", message, "Nov 4"));
        mailList.add(new Mail(4, "Peter Cartlsson", message, "Nov 3"));
        mailList.add(new Mail(5, "Trevor Hansen", message, "Nov 2"));
        mailList.add(new Mail(6, "Britta Holt", message, "Nov 2"));
        mailList.add(new Mail(7, "Sandra Adams", message, "Nov 2"));
        mailList.add(new Mail(8, "Cristopher Oyarzún", "Yeah!!", "Nov 2"));
    }

	private int setColorFilter(int color) {
		if ((color % 4) == 0) {
			return ContextCompat.getColor(getApplicationContext(), R.color.one_round);
		}
		if ((color % 3) == 0) {
			return ContextCompat.getColor(getApplicationContext(), R.color.two_round);
		}
		if ((color % 2) == 0) {
			return ContextCompat.getColor(getApplicationContext(), R.color.three_round);
		} else {
			return ContextCompat.getColor(getApplicationContext(), R.color.four_round);
		}
    }

    static class ViewHolder {
        @Bind(R.id.image_email) ImageView imageEmail;
        @Bind(R.id.text_label_email)  TextView textLabelEmail;
        @Bind(R.id.text_title_email)  TextView textTitleEmail;
        @Bind(R.id.text_message_email)  TextView textMessageEmail;
        @Bind(R.id.text_date_email)  TextView textDateEmail;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

	private class MailAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mailList.size();
		}

		@Override
		public Mail getItem(int position) {
			return mailList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(MainActivity.this, R.layout.list_item_mail, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.imageEmail.setColorFilter(setColorFilter(mailList.get(position).getCircleColor()));
			holder.textLabelEmail.setText(mailList.get(position).getTitleEmail().substring(0, 1));
			holder.textTitleEmail.setText(mailList.get(position).getTitleEmail());
			holder.textMessageEmail.setText(mailList.get(position).getMessageEmail());
			holder.textDateEmail.setText(mailList.get(position).getDateEmail());

			return convertView;
		}
    }

}
