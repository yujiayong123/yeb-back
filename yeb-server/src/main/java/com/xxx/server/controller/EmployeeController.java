package com.xxx.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.xxx.server.pojo.*;
import com.xxx.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有员工(分页)")
    @GetMapping(value = "/")
    public RespPageBean getEmployeeInfo(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        Employee employee,
                                        LocalDate[] beginDateScope) {
        return employeeService.getEmployeeInfo(currentPage,size,employee,beginDateScope);
    }

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/getAllPoliticsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/getAllNations")
    public List<Nation> getAllNations() {
        return nationService.list();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/getAllPosition")
    public List<Position> getAllPosition() {
        return positionService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/getAllJobLevel")
    public List<Joblevel> getAllJobLevel() {
        return joblevelService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/getAllDepartment")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/getWorkID")
    public RespBean getWorkID() {
        return employeeService.getWorkID();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping(value = "/addDep")
    public RespBean addDep(@RequestBody Employee employee) {
        return employeeService.addDep(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping(value = "/updateDep")
    public RespBean updateDep(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping(value = "/{id}")
    public RespBean updateDep(@PathVariable("id") Integer id) {
        if (employeeService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "导出员工表")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void export(HttpServletResponse response) {
        List<Employee> list = employeeService.getEmp(null);
        ExportParams params = new ExportParams("员工表","员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream out = null;
        try {
//            流形式
            response.setHeader("content-type","application/octet-stream");
//            防止中文乱码
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "导入员工数据")
    @GetMapping("/import")
    public RespBean importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();
//        去掉标题行
        params.setTitleRows(1);
        List<Nation> nationList = nationService.list();
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        List<Position> positionList = positionService.list();
        List<Joblevel> joblevelList = joblevelService.list();
        List<Department> departmentList = departmentService.list();

        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            list.forEach(employee -> {
//                民族id
                employee.setNationId(nationList.get(list.indexOf(new Nation(employee.getNation().getName()))).getId());
//                政治面貌id
                employee.setPoliticId(politicsStatusList.get(list.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
//                职位id
                employee.setPosId(positionList.get(list.indexOf(new Position(employee.getPosition().getName()))).getId());
//                职称id
                employee.setJobLevelId(joblevelList.get(list.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
//                部门id
                employee.setDepartmentId(departmentList.get(list.indexOf(new Department(employee.getDepartment().getName()))).getId());
            });
            if (employeeService.saveBatch(list)) {
                return RespBean.success("导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败");
    }

}
