package com.house.controller;

import com.house.common.Result;
import com.house.common.StatusCode;
import com.house.dto.PaidExecution;
import com.house.pojo.Paid;
import com.house.service.PaidService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "租金管理")
@RestController
@CrossOrigin
@RequestMapping(value="/paid")
public class PaidController {

    @Autowired
    private PaidService paidService;

    @ApiOperation("查找租金信息列表")
    @RequestMapping(value = "/getallpaidlist",method = RequestMethod.GET)
    public Result getAllPaidList(){
        return new Result(true, StatusCode.SUCCESS,"查找租金信息列表成功",paidService.findPaidListByCondition(null,null,null,null));
    }

    @ApiOperation("按条件查找租金信息列表")
    @RequestMapping(value = "/getpaidlistbycondition",method = RequestMethod.POST)
    public Result getPaidListByCondition(@RequestBody Paid paid){
        return new Result(true, StatusCode.SUCCESS,"按条件查找租金信息列表成功",paidService.findPaidListByCondition(paid.getStatus(),paid.getName(),paid.getAddress(),paid.getUserlist_id()));
    }

    @ApiOperation("添加租金信息")
    @RequestMapping(value="/addpaid",method = RequestMethod.POST)
    public Result addPaid(@RequestBody Paid paid){
        PaidExecution pe;
        try{
            pe = paidService.addPaid(paid);
            if(pe.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"添加租金信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"添加租金信息失败：" + pe.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"添加租金信息失败：" + e.toString());
        }
    }

    @ApiOperation("修改租金信息")
    @RequestMapping(value="/updatepaid",method = RequestMethod.POST)
    public Result updatePaid(@RequestBody Paid paid){
        PaidExecution pe;
        try{
            pe = paidService.updatePaid(paid);
            if(pe.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"修改租金信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"修改租金信息失败：" + pe.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"修改租金信息失败：" + e.toString());
        }
    }

    @ApiOperation("删除租金信息")
    @RequestMapping(value="/deletepaid",method = RequestMethod.DELETE)
    public Result deletePaid(@RequestParam("paidId")Integer paidId){
        PaidExecution pe;
        try{
            pe = paidService.deletePaid(paidId);
            if(pe.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"删除租金信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"删除租金信息失败：" + pe.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"删除租金信息失败：" + e.toString());
        }
    }
}
