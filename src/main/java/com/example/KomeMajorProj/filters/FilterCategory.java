package com.example.KomeMajorProj.filters;

import android.widget.Filter;

import com.example.KomeMajorProj.adapters.AdaptCategory;
import com.example.KomeMajorProj.models.ModelCategory;

import java.util.ArrayList;

public class FilterCategory extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelCategory> filterList;
    //adapter in which filter need to be implemented
    AdaptCategory adapterCat;

    //constructor
    public FilterCategory(ArrayList<ModelCategory> filterList, AdaptCategory adapterCat) {
        this.filterList = filterList;
        this.adapterCat = adapterCat;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //value should not be null and empty
        if (constraint != null && constraint.length() > 0){

            //change to upper case, or lower case to avoid case sensitivity
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelCategory> filteredModels = new ArrayList<>();

            for (int i=0; i<filterList.size(); i++){
                //validate
                if (filterList.get(i).getCategory().toUpperCase().contains(constraint)){
                    //add to filtered list
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;

        }
        else {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results; //dont miss it
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        //apply fiter cahnges
        adapterCat.categoryArrayList = (ArrayList<ModelCategory>)results.values;

        //notify cahnges
        adapterCat.notifyDataSetChanged();
    }
}
