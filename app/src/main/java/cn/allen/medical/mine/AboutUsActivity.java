package cn.allen.medical.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import allen.frame.AllenBaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.R;

public class AboutUsActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_about_us)
    AppCompatTextView tvAboutUs;


    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        toolbar.setTitle("关于我们");
        actHelper.setToolbarTitleCenter(toolbar,"关于我们");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        tvAboutUs.setText("医院赋能、给患者减负是我们坚守的信条，凭借领先的 技术和优质的服务赢得客户的信赖。\n\n        " +
                "重庆博尔德医疗科技股份有限公司位于重庆两江新区空\n" +
                "港保税区内，以三十年医疗领域的成熟经验，结合前沿科学\n" +
                "技术，致力于打造一个基于互联网云平台的医用药品和耗材\n" +
                "的现代化管理和物流体系，为医院提供药品和耗材供应链管\n" +
                "理和运营的完整解决方案。        公司拥有完全自主知识产权的医用耗材管理云平台，拥\n" +
                "有一万平方米的常温库、阴凉库、冷藏库、冷冻库，配备\n" +
                "WMS仓储管理软件，TMS运输监控软件，CCTS全程冷链系\n" +
                "统。配备各种运输车辆，包括带有GPS定位和24小时不间断\n" +
                "温度监控的冷链运输车辆，可提供全天候的配送服务。公司\n" +
                "先后获得了商标证书22件，取得国家医疗器械经营许可证、\n" +
                "药品经营许可证、计算机软件著作权登记证书、软件产品证\n" +
                "书、软件企业证书，自主研发的平台获得了软件产品登记测\n" +
                "试报告，自主开发的智能终端获得了两件发明专利，一件实\n" +
                "用新型专利，多项行业领先的技术已经申报国家发明专利和\n" +
                "实用新型专利。");
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
