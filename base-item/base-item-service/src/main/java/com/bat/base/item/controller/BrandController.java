package com.bat.base.item.controller;

import com.bat.base.item.pojo.Brand;
import com.bat.base.item.service.BrandService;
import com.bat.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("brand")
@Slf4j
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping
    public ResponseEntity<Void> addBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        try{
            log.info("addBrand，parameter：{} , {}",brand, cids);
            this.brandService.addBrand(brand, cids);
        }catch (Exception e){
            log.info("addBrand failure");
            return ResponseEntity.badRequest().build();
        }
        log.info("addBrand success");
        return ResponseEntity.ok().build();
    }

    @GetMapping("selectAll")
    public ResponseEntity<List<Brand>> selectAll(){
        List<Brand> brandList = this.brandService.queryBrands();
        if(CollectionUtils.isEmpty(brandList)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandList);
    }

    /***
     * 根据查询条件，分页并排序查询品牌
     * @param key
     * @param page
     * @param rows
     * @param orderBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> pageQueryBrands(
            @RequestParam(value ="key", required = false)String key,
            @RequestParam(value="page", defaultValue = "1")Integer page,
            @RequestParam(value="rows", defaultValue = "10")Integer rows,
            @RequestParam(value = "orderBy", required = false)String orderBy,
            @RequestParam(value="desc", required = false)Boolean desc
    ){
        PageResult<Brand> result = this.brandService.pageQueryBrands(key, page, rows, orderBy, desc);
        if(CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }


    public void bubbleSort(int[] arr){
        System.out.println("冒泡排序");
        for(int i=0; i<arr.length-1; i++){
            for(int j=i; j<arr.length-i-1; j++){
                if(arr[j]>arr[j+1]){
                    arr[j]+=arr[j+1];
                    arr[j+1] = arr[j]-arr[j+1];
                    arr[j]-=arr[j+1];
                }
            }
        }
    }

    /**
     * 终极版冒泡排序
     * 加入一个布尔变量,如果内循环没有交换值,说明已经排序完成,提前终止
     * @param arr
     */
    public static void sortPlus(int[] arr){
        if(arr != null && arr.length > 1){
            for(int i = 0; i < arr.length - 1; i++){
                // 初始化一个布尔值
                boolean flag = true;
                for(int j = 0; j < arr.length - i - 1 ; j++){
                    if(arr[j] > arr[j+1]){
                        // 调换
                        int temp;
                        temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = temp;
                        // 改变flag
                        flag = false;
                    }
                }
                if(flag){
                    break;
                }
            }
        }
    }

    public void doNothing(){
        System.out.println("Monday");
        System.out.println("周二");
        System.out.println("周三");
        System.out.println("Thursday");
        System.out.println("Firday");
    }


}
