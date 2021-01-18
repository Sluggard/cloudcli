package com.sluggard.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.sluggard.enums.Gender;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2021/1/18 18:29
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
public class Gender2StringConverter implements Converter<Gender> {
    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Gender convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Gender result = Gender.SECRET;
        if(cellData.getStringValue().equals(Gender.MAN.getDesc())){
            result = Gender.MAN;
        }
        if(cellData.getStringValue().equals(Gender.FEMALE.getDesc())){
            result = Gender.FEMALE;
        }
        return result;
    }

    @Override
    public CellData convertToExcelData(Gender gender, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(gender.getDesc());
    }
}
