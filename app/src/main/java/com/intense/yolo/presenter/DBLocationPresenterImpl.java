package com.intense.yolo.presenter;

import com.intense.yolo.entity.DBLocationInfo;
import com.intense.yolo.model.DBLocationModel;
import com.intense.yolo.model.DBLocationModelImpl;
import com.intense.yolo.view.DBLocationView;

import java.util.List;

public class DBLocationPresenterImpl implements DBLocationPresenter, DBLocationModel.OnDBLocationCheckListener {

    private DBLocationView dbLocationView;
    private DBLocationModel dbLocationModel;

    public DBLocationPresenterImpl(DBLocationView dbLocationView) {
        this.dbLocationView = dbLocationView;
        this.dbLocationModel = new DBLocationModelImpl();
    }


    @Override
    public void getValidateLocation() {
        if (dbLocationView != null) {
            dbLocationView.showProgress();
            dbLocationModel.getDBLocation(this);
        }
    }

    @Override
    public void onDBLocationSuccess(List<DBLocationInfo> infoList) {
        if (dbLocationView != null) {
            dbLocationView.hideProgress();
            dbLocationView.listDBLocation(infoList);
        }
    }

    @Override
    public void onDBLocationError() {
        if (dbLocationView != null) {
            dbLocationView.hideProgress();
            dbLocationView.setDBLocationError();
        }
    }

    @Override
    public void onDBLocationFailure(String message) {
        if (dbLocationView != null) {
            dbLocationView.hideProgress();
            dbLocationView.showAlert(message);
        }
    }
}