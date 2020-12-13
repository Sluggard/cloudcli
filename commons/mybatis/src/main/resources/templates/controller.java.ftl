package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>


/**
 * <p>
    * ${table.comment!} 前端控制器
    * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@Api(value="${table.controllerName}",tags={"${table.comment?replace("表","")}操作接口"})
@RequestMapping("/v1<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @ApiOperation(value = "查询${table.comment?replace("表","")}", notes = "查询${table.comment?replace("表","")}")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(${table.serviceName?uncap_first}.getById(id));
    }

    @ApiOperation(value = "更新${table.comment?replace("表","")}", notes = "更新${table.comment?replace("表","")}")
    @ApiImplicitParam(name="${table.entityName?uncap_first}",value = "${table.comment?replace("表","")}",dataType = "${table.entityName}",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody ${table.entityName} ${table.entityName?uncap_first}) {
        <#list table.fields as field>
        <#if field.name='updated_time'>
        ${table.entityName?uncap_first}.setUpdatedTime(new Date());
        </#if>
        </#list>
        ${table.serviceName?uncap_first}.saveOrUpdate(${table.entityName?uncap_first});
        return ResponseResult.ok(${table.entityName?uncap_first});
    }

    @ApiOperation(value = "新增${table.comment?replace("表","")}", notes = "新增${table.comment?replace("表","")}")
    @ApiImplicitParam(name="${table.entityName?uncap_first}",value = "${table.comment?replace("表","")}",dataType = "${table.entityName}",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody ${table.entityName} ${table.entityName?uncap_first}) {
        <#list table.fields as field>
        <#if field.name='created_time'>
        ${table.entityName?uncap_first}.setCreatedTime(new Date());
        </#if>
        <#if field='updated_time'>
        ${table.entityName?uncap_first}.setUpdatedTime(new Date());
        </#if>
        </#list>
        ${table.serviceName?uncap_first}.saveOrUpdate(${table.entityName?uncap_first});
        return ResponseResult.ok(${table.entityName?uncap_first});
    }

    @ApiOperation(value = "删除${table.comment?replace("表","")}", notes = "删除${table.comment?replace("表","")}")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(${table.serviceName?uncap_first}.removeById(id));
    }

    @ApiOperation(value = "分页条件查询${table.comment?replace("表","")}", notes = "分页条件查询${table.comment?replace("表","")}")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<${table.entityName}> pageQuery) {
        return ResponseResult.ok(${table.serviceName?uncap_first}.page(pageQuery.parsePage()));
    }
}
</#if>
