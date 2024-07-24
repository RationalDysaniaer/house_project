package com.house.controller;

import com.house.common.Result;
import com.house.common.StatusCode;
import com.house.dto.SolveExecution;
import com.house.pojo.Solve;
import com.house.service.SolveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "故障信息列表")
@RestController
@CrossOrigin
@RequestMapping(value="/solve")
public class SolveController {

    @Autowired
    private SolveService solveService;

    @ApiOperation("查找故障信息列表")
    @RequestMapping(value = "/getallsolvelist",method = RequestMethod.GET)
    public Result getAllSolveList(){
        return new Result(true, StatusCode.SUCCESS,"查找故障信息列表成功",solveService.findSolveListByCondition(null,null,null,null));
    }

    @ApiOperation("按条件查找故障信息列表")
    @RequestMapping(value = "/getsolvelistbycondition",method = RequestMethod.POST)
    public Result getSolveListByCondition(@RequestBody Solve solve){
        return new Result(true, StatusCode.SUCCESS,"按条件查找故障信息列表成功",solveService.findSolveListByCondition(solve.getStatus(),solve.getName(),solve.getAddress(),solve.getUserlist_id()));
    }

    @ApiOperation("添加故障信息")
    @RequestMapping(value="/addsolve",method = RequestMethod.POST)
    public Result addSolve(@RequestBody Solve solve){
        SolveExecution se;
        try{
            se = solveService.addSolve(solve);
            if(se.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"添加故障信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"添加故障信息失败：" + se.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"添加故障信息失败：" + e.toString());
        }
    }

    @ApiOperation("修改故障信息")
    @RequestMapping(value="/updatesolve",method = RequestMethod.POST)
    public Result updateSolve(@RequestBody Solve solve){
        SolveExecution se;
        try{
            se = solveService.updateSolve(solve);
            if(se.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"修改故障信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"修改故障信息失败：" + se.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"修改故障信息失败：" + e.toString());
        }
    }

    @ApiOperation("删除故障信息")
    @RequestMapping(value="/deletesolve",method = RequestMethod.DELETE)
    public Result deleteSolve(@RequestParam("solveId")Integer solveId){
        SolveExecution se;
        try{
            se = solveService.deleteSolve(solveId);
            if(se.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"删除故障信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"删除故障信息失败：" + se.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"删除故障信息失败：" + e.toString());
        }
    }
}
