/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allfine.models;

import java.util.ArrayList;

import com.allfine.models.core.CoreModel;

/**
 *
 * @author eypirimov
 */
public class ActionInfoModelArrayList extends CoreModel{
    
    private ArrayList<ActionInfoModel> actionInfoList;

    public ArrayList<ActionInfoModel> getActionInfoList() {
        return actionInfoList;
    }

    public void setActionInfoList(ArrayList<ActionInfoModel> actionInfoList) {
        this.actionInfoList = actionInfoList;
    }
    
}
