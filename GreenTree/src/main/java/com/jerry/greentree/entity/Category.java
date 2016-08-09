package com.jerry.greentree.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-1-5.
 */
public class Category
{
    public String categoryId;

    public String categoryName;

    public List<Category> subList = new ArrayList<>();
}
