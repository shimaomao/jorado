package com.jorado.dict;

import com.jorado.core.util.IOUtils;
import com.jorado.core.util.JsonUtils;
import com.jorado.core.util.StringUtils;
import com.jorado.dict.location.*;

import java.util.*;

public final class DictUtils {

    private static final Map<String, HashMap<String, BaseDataInfo>> BASE_DATA_DICT = new HashMap<>();
    private static Location location;
    private static Map<String, String> locationMap;
    private static Map<String, String> mapLocation;
    private static Map<String, String> schoolMap;
    private static Map<String, String> majorMap;

    static {
        locationMap = new LinkedHashMap<>();
        mapLocation = new LinkedHashMap<>();
        schoolMap = new LinkedHashMap<>();
        majorMap = new LinkedHashMap<>();

        location = JsonUtils.fromJson(IOUtils.readResource("location.json"), Location.class);

        setLocationMap();
        setMapLocation();

        schoolMap = JsonUtils.fromJson(IOUtils.readResource("school.json"), schoolMap.getClass());
        majorMap = JsonUtils.fromJson(IOUtils.readResource("major.json"), majorMap.getClass());

    }

    private DictUtils() {

    }

    /**
     * 获取地区基础数据
     *
     * @return
     */
    public static Location getLocation() {
        return location;
    }

    public static Map<String, String> getLocationMap() {
        return locationMap;
    }

    public static Map<String, String> getMapLocation() {
        return mapLocation;
    }

    public static String getSchoolName(String code) {
        code = code.trim();
        if (!schoolMap.containsKey(code)) {
            return "";
        }
        return schoolMap.get(code);
    }

    public static String getMajorName1(String code) {
        code = code.trim();
        if (!majorMap.containsKey(code)) {
            return "";
        }
        return majorMap.get(code);
    }

    /**
     * 根据code获取城市名称
     *
     * @param code
     * @return
     */
    public static String getLocationName(String code) {
        code = code.trim();
        if (!mapLocation.containsKey(code)) {
            return "";
        }
        return mapLocation.get(code);
    }

    /**
     * 根据名称获取城市code
     *
     * @param name
     * @return
     */
    public static String getLocationCode(String name) {
        name = name.trim();
        if (!locationMap.containsKey(name)) {
            return "";
        }
        return locationMap.get(name);
    }

    /**
     * 移除城市
     *
     * @param input
     * @return
     */
    public static String removeCity(String input) {
        return removeCity(input, "");
    }

    /**
     * 移除城市
     *
     * @param input
     * @param exclude
     * @return
     */
    public static String removeCity(String input, String exclude) {
        List<String> excludes = StringUtils.split(exclude, ",");
        for (Map.Entry<String, String> entry : DictUtils.getLocationMap().entrySet()) {
            String k = entry.getKey().replace("市", "");
            k = k.replace("县", "");
            k = k.replace("区", "");
            if (k.length() < 2 || excludes.contains(k)) {
                continue;
            }
            input = input.replace(k + "市", "");
            input = input.replace(k + "县", "");
            input = input.replace(k + "区", "");
            input = input.replace(k, "");
        }

        return input;
    }

    /**
     * 获取城区编码获取省份编码
     *
     * @param regionCode
     * @return
     */
    public static String getProvinceCodeByRegion(String regionCode) {
        String key = "city_relation";
        if (BASE_DATA_DICT.get(key) == null) {
            initData(key);
        }
        String provinceCode = regionCode;
        //如果为区，转换为市
        if (regionCode.length() == 4) {
            provinceCode = getCityCodeByRegion(regionCode);
        }
        //转换为省
        if (BASE_DATA_DICT.containsKey(key)) {
            HashMap<String, BaseDataInfo> dict = BASE_DATA_DICT.get(key);
            BaseDataInfo basedata = dict.get(provinceCode);
            //如果为直辖市，直接返回，如果为普通市，返回省
            if (basedata != null && !"489".equals(basedata.parentCode) && !"0".equals(basedata.parentCode)) {
                return basedata.parentCode;
            }
        }
        return provinceCode;
    }

    /**
     * 获取城区编码获取省份名称
     *
     * @param regionCode
     * @return
     */
    public static String getProvinceNameByRegion(String regionCode) {
        return getLocationName(getProvinceCodeByRegion(regionCode));
    }

    /**
     * 获取城区编码获取城市编码
     *
     * @param regionCode
     * @return
     */
    public static String getCityCodeByRegion(String regionCode) {
        String citycode = regionCode;
        String key = "region";
        if (BASE_DATA_DICT.get(key) == null) {
            initData(key);
        }

        if (BASE_DATA_DICT.containsKey(key)) {
            HashMap<String, BaseDataInfo> dict = BASE_DATA_DICT.get(key);
            BaseDataInfo basedata = dict.get(regionCode);
            if (basedata != null) {
                citycode = basedata.parentCode;
            }
        }

        return citycode;
    }

    /**
     * 获取城区编码获取城市名称
     *
     * @param regionCode
     * @return
     */
    public static String getCityNameByRegion(String regionCode) {
        return getLocationName(getCityCodeByRegion(regionCode));
    }

    /**
     * 工作经验
     *
     * @param code
     * @return
     */
    public static String getWorkingTime(String code) {
        String key = "working_time";
        return getData(key, code, 1);
    }

    /**
     * 公司类型
     *
     * @param code
     * @return
     */
    public static String getCompanyType(String code) {
        String key = "company_type";
        return getData(key, code, 1);
    }

    /**
     * 公司规模
     *
     * @param code
     * @return
     */
    public static String getCompanySize(String code) {
        String key = "company_size";
        return getData(key, code, 1);
    }

    /**
     * 获取性别
     *
     * @param code
     * @return
     */
    public static String getGender(String code) {
        String key = "gender";
        return getData(key, code, 1);
    }

    /**
     * 获取职位类别名称
     *
     * @param code 编号
     * @return
     */
    public static String getJobTypeName(String code) {
        String key = "job_type";
        return getData(key, code, 1);
    }

    /**
     * 获取职位小类名称
     *
     * @param code 编号
     * @return
     */
    public static String getSubJobTypeName(String code) {
        String key = "sub_job_type";
        code = StringUtils.padLeft(code, 3, '0');
        return getData(key, code, 1);
    }

    /**
     * 获取父类别编码
     *
     * @param code
     * @return
     */
    public static String getParentTypeCode(String code) {
        String key = "sub_job_type";
        code = StringUtils.padLeft(code, 3, '0');
        return getData(key, code);
    }

    /**
     * 获取父类别名称
     *
     * @param code
     * @return
     */
    public static String getParentTypeName(String code) {
        code = StringUtils.padLeft(code, 3, '0');
        return getJobTypeName(getParentTypeCode(code));
    }

    /**
     * 获取城市名称
     *
     * @param code 编号
     * @return
     */
    public static String getCityName(String code) {
        String key = "city";
        return getData(key, code, 1);
    }

    /**
     * 获取城区名称
     *
     * @param code
     * @return
     */
    public static String getRegionName(String code) {
        String key = "region";
        return getData(key, code, 1);
    }

    /**
     * 获取专业名称
     *
     * @param code
     * @return
     */
    public static String getMajorName(String code) {
        String key = "edu_type";
        return getData(key, code, 1);
    }

    /**
     * 获取职位类别名称
     *
     * @param code
     * @param language
     * @return
     */
    public static String getJobTypeName(String code, int language) {
        String key = "job_type";
        return getData(key, code, language);
    }

    /**
     * 获取职位小类名称
     *
     * @param code
     * @param language
     * @return
     */
    public static String getSubJobTypeName(String code, int language) {
        String key = "sub_job_type";
        code = StringUtils.padLeft(code, 3, '0');
        return getData(key, code, language);
    }

    /**
     * 获取行业名称
     *
     * @param code     编号
     * @param language 语言 1 中文 2 英文 （默认中文）
     * @return
     */
    public static String getIndustryName(String code, int language) {
        String key = "industry";
        return getData(key, code, language);
    }

    /**
     * 获取婚姻状况
     *
     * @param code     编号
     * @param language 语言 1 中文 2 英文 （默认中文）
     * @return
     */
    public static String getMaritalStatusName(String code, int language) {
        String key = "marital_status";
        return getData(key, code, language);
    }

    /**
     * 获取学历名称
     *
     * @param code     编号
     * @param language 语言 1 中文 2 英文 （默认中文）
     * @return
     */
    public static String getEducationName(String code, int language) {
        String key = "education";
        return getData(key, code, language);
    }

    /**
     * 获取政治面貌
     *
     * @param id       编号
     * @param language 语言 1 中文 2 英文 （默认中文）
     * @return
     */
    public static String getPoliticalName(String id, int language) {
        String key = "political";
        return getData(key, id, language);
    }

    public static Map<String, String> getSmallJobType(String parentCode, int language) {
        String key = "sub_job_type";
        return getChildBaseData(key, parentCode, language);
    }

    public static List<String> getSmallJobTypeList(String parentCode, int language) {
        String key = "sub_job_type";
        Map<String, String> jobTypeMap = getChildBaseData(key, parentCode, language);
        List<String> smallJobTypes = new ArrayList<>();
        Iterator<String> iterator = jobTypeMap.keySet().iterator();
        while (iterator.hasNext()) {
            String childCode = iterator.next();
            smallJobTypes.add(childCode);
        }
        return smallJobTypes;
    }

    private static Map<String, String> getChildBaseData(String key, String parentCode, int language) {

        if (!BASE_DATA_DICT.containsKey(key) || BASE_DATA_DICT.get(key) == null) {
            initData(key);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        if (BASE_DATA_DICT.containsKey(key)) {
            HashMap<String, BaseDataInfo> dict = BASE_DATA_DICT.get(key);
            Iterator<String> itor = dict.keySet().iterator();
            while (itor.hasNext()) {
                String childCode = itor.next();
                BaseDataInfo basedata = dict.get(childCode);
                if (basedata.getParentCode().equals(parentCode)) {
                    map.put(childCode, language == 1 ? basedata.cnName
                            : basedata.enName);
                }
            }
        }
        return map;
    }

    /**
     * 填充数据字典
     *
     * @param type
     */
    private static void initData(String type) {
        try {

            String baseData = IOUtils.readResource(type + ".txt");

            if (StringUtils.isNullOrWhiteSpace(baseData)) {
                return;
            }
            BASE_DATA_DICT.put(type, convertData(baseData, type));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static HashMap<String, BaseDataInfo> convertData(String content, String type) {

        HashMap<String, BaseDataInfo> singleTypeDic = new HashMap<String, BaseDataInfo>();
        if (StringUtils.isNullOrWhiteSpace(content)) {
            return singleTypeDic;
        }

        String[] itemArr = StringUtils.splitString(content, "[\\{\\$\\}]", true);
        for (String v : itemArr) {
            String[] subItemArr = StringUtils.splitString(v, "\\|", false);
            if ("sub_job_type".equalsIgnoreCase(type)) {
                singleTypeDic.put(subItemArr[2], new BaseDataInfo(
                        subItemArr[3], subItemArr[4], subItemArr[0]));
            } else if ("city_relation".equalsIgnoreCase(type)) {
                try {
                    singleTypeDic.put(subItemArr[0], new BaseDataInfo(subItemArr[1], subItemArr[2], subItemArr[subItemArr.length > 4 ? 4 : 3]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if ("region".equalsIgnoreCase(type)) {
                singleTypeDic.put(subItemArr[0], new BaseDataInfo(
                        subItemArr[1], subItemArr[2], subItemArr[3]));
            } else {
                singleTypeDic.put(subItemArr[0], new BaseDataInfo(
                        subItemArr[1], subItemArr[2]));
            }
        }
        return singleTypeDic;
    }

    /**
     * 获取基本数据
     *
     * @param key      类型编号
     * @param valueId  编号
     * @param language 语言 1 中文 2 英文 （默认中文）
     * @return
     */
    private static String getData(String key, String valueId, int language) {

        if (BASE_DATA_DICT.get(key) == null) {
            initData(key);
        }
        String value = "";
        if (BASE_DATA_DICT.containsKey(key)) {
            HashMap<String, BaseDataInfo> dict = BASE_DATA_DICT.get(key);
            BaseDataInfo basedata = dict.get(valueId);
            if (basedata != null) {
                value = language == 1 ? basedata.cnName : basedata.enName;
            }
        }
        return value;
    }

    /**
     * 获取基本数据
     *
     * @param key     类型编号
     * @param valueId 编号
     * @return
     */
    private static String getData(String key, String valueId) {

        if (!BASE_DATA_DICT.containsKey(key) || BASE_DATA_DICT.get(key) == null) {
            initData(key);
        }
        String value = "";
        if (BASE_DATA_DICT.containsKey(key)) {
            HashMap<String, BaseDataInfo> dict = BASE_DATA_DICT.get(key);
            BaseDataInfo basedata = dict.get(valueId);
            if (basedata != null) {
                value = basedata.getParentCode();
            }
        }
        return value;
    }

    /**
     * 初始化地区字典，方便快速根据name查找code（name->code）
     */
    private static void setLocationMap() {
        if (locationMap.isEmpty() && null != location) {
            locationMap.put("中国", "0");
            //省
            for (Province p : location.getProvince()) {
                String name = p.getName();
                if (!locationMap.containsKey(name)) {
                    locationMap.put(name, p.getCode());
                }

                for (Sublist sb : p.getSublist()) {

                    name = sb.getName();
                    if (!locationMap.containsKey(name)) {
                        locationMap.put(name, sb.getCode());
                    }

                    for (Sublist2 sb2 : sb.getSublist()) {
                        name = sb2.getName();
                        if (!locationMap.containsKey(name)) {
                            locationMap.put(name, sb2.getCode());
                        }
                    }
                }
            }

            //全国
            for (All p : location.getAll()) {
                String name = p.getName();
                if (!locationMap.containsKey(name)) {
                    locationMap.put(name, p.getCode());
                }
            }

            //其他
            for (Other p : location.getOther()) {
                String name = p.getName();
                if (!locationMap.containsKey(name)) {
                    locationMap.put(name, p.getCode());
                }

                for (Sublist2 sb2 : p.getSublist()) {

                    name = sb2.getName();
                    if (!locationMap.containsKey(name)) {
                        locationMap.put(name, sb2.getCode());
                    }

                }
            }

            //热门城市
            for (Hotcity p : location.getHotcitys()) {
                String name = p.getName();
                if (!locationMap.containsKey(name)) {
                    locationMap.put(name, p.getCode());
                }

                for (Sublist sb : p.getSublist()) {

                    name = sb.getName();
                    if (!locationMap.containsKey(name)) {
                        locationMap.put(name, sb.getCode());
                    }

                    for (Sublist2 sb2 : sb.getSublist()) {
                        name = sb2.getName();
                        if (!locationMap.containsKey(name)) {
                            locationMap.put(name, sb2.getCode());
                        }

                    }
                }
            }


        }
    }

    /**
     * 初始化地区字典，方便快速根据code查找name（code->name）
     */
    private static void setMapLocation() {

        if (mapLocation.isEmpty() && !locationMap.isEmpty()) {

            for (Map.Entry<String, String> entry : locationMap.entrySet()) {
                String v = entry.getValue();
                if (!mapLocation.containsKey(v)) {
                    mapLocation.put(v, entry.getKey());
                }
            }
        }
    }

    static final class BaseDataInfo {

        private String cnName;
        private String enName;
        private String parentCode;

        public BaseDataInfo(String cnName, String enName) {
            super();
            this.cnName = cnName;
            this.enName = enName;
        }

        public BaseDataInfo(String cnName, String enName, String parentCode) {
            super();
            this.cnName = cnName;
            this.enName = enName;
            this.parentCode = parentCode;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }
    }
}
