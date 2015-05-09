/**
 *
 *          商家行业
 *
 * Created by Satan on 14-6-10.
 */


function createIndustry(id, name, superid) {
    var Industry = new Object;
    Industry.id = id;
    Industry.name = name;
    Industry.superid = superid;
    return Industry;
}


var IndustryList = new Array(
    createIndustry(11, '医药', 0),
    createIndustry(10, '教育', 0),
    createIndustry(12, '娱乐', 0),
    createIndustry(13, '汽车', 0),
    createIndustry(14, '旅游业', 0),
    createIndustry(15, '服装', 0),
    createIndustry(16, '房地产', 0),
    createIndustry(17, '装潢装饰', 0),
    createIndustry(18, '酒水', 0),
    createIndustry(19, '中介', 0),
    createIndustry(20, '财务公司', 0),
    createIndustry(21, '孕婴', 0),
    createIndustry(22, '黄金珠宝', 0),
    createIndustry(23, '商场', 0),
    createIndustry(24, '餐饮', 0),
    createIndustry(25, '政府机关', 0),
    createIndustry(26, '医院', 0),
    createIndustry(27, '银行', 0),
    createIndustry(28, '综合(未知)', 0),
    createIndustry(1001, '全日制学校', 10),
    createIndustry(1002, '成人大专', 10),
    createIndustry(1003, '培训学校', 10),
    createIndustry(1004, '教育局', 10),
    createIndustry(1005, '综合(未知)', 10),
    createIndustry(1201, 'KTV,酒吧', 12),
    createIndustry(1202, '台球,棋牌,茶楼', 12),
    createIndustry(1301, '汽车销售', 13),
    createIndustry(1302, '汽车租赁', 13),
    createIndustry(1303, '汽车服务', 13),
    createIndustry(1401, '宾馆', 14),
    createIndustry(1402, '旅行社', 14),
    createIndustry(1403, '景区', 14),
    createIndustry(1501, '男装', 15),
    createIndustry(1502, '女装', 15),
    createIndustry(1503, '童装', 15),
    createIndustry(1701, '装潢装饰材料', 17),
    createIndustry(1702, '装潢装饰公司', 17),
    createIndustry(1801, '啤酒', 18),
    createIndustry(1802, '白酒', 18),
    createIndustry(1803, '红酒', 18),
    createIndustry(1804, '水', 18),
    createIndustry(1805, '饮料', 18),
    createIndustry(1901, '房产中介', 19),
    createIndustry(1902, '婚介', 19),
    createIndustry(2401, '西餐', 24),
    createIndustry(2402, '中餐', 24),
    createIndustry(2501, '政府', 25),
    createIndustry(2502, '国税局', 25),
    createIndustry(2503, '社保局', 25),
    createIndustry(2504, '国土资源局', 25),
    createIndustry(2505, '财政局', 25),
    createIndustry(2506, '地税局', 25),
    createIndustry(2507, '林业局', 25),
    createIndustry(2508, '卫生局', 25),
    createIndustry(2509, '民政局', 25),
    createIndustry(2510, '村民委员会', 25),
    createIndustry(2511, '粮所', 25),
    createIndustry(2901, '招聘', 29),
    createIndustry(2902, '转让', 29),
    createIndustry(2903, '求租', 29),
    createIndustry(2904, '求购', 29),
    createIndustry(2905, '求职', 29),
    createIndustry(2906, '征婚', 29),
    createIndustry(2907, '征友', 29),
    createIndustry(2908, '出租', 29),
    createIndustry(2909, '出售', 29),
    createIndustry(100101, '幼儿园', 1001),
    createIndustry(100102, '小学', 1001),
    createIndustry(100103, '中学', 1001),
    createIndustry(100104, '高中', 1001),
    createIndustry(100105, '大学', 1001),
    createIndustry(100106, '中专', 1001),
    createIndustry(100201, '党校', 1002),
    createIndustry(100202, '驾校', 1002)
);


function getOrderList(superid) {
    var orderlist = new Array();

    for (var i in IndustryList) {
        if (IndustryList[i].superid == superid) {
            orderlist.push(IndustryList[i]);
        }
    }

    return orderlist;
}

function getmainselect() {
    var orderlist = getOrderList(0);

    var htmlstr = "";
    for (var indu in orderlist) {
        htmlstr = htmlstr + "<option value ='" + orderlist[indu].id + "'>" + orderlist[indu].name + "</option>";
    }
    document.write(htmlstr);
}

function shownext(x) {

    var sls = document.getElementById("IndustrySelectDIV").getElementsByTagName('select');
    var count = sls.length;
    if (count > 1) {
        for(var i=0;i< count;i++){
            if (sls[i - (count - sls.length)].id != "IndustrySelect" && !compare(sls[i - (count - sls.length)].id,x)) {
                    document.getElementById("IndustrySelect").parentNode.removeChild(sls[i - (count - sls.length)]);
            }
        }
    }


    var orderlist = getOrderList(x);
    if (orderlist.length > 0) {
        var nextselect = document.createElement("select");
        nextselect.id = x;
        nextselect.onchange = function(){
            shownext(this.options[this.selectedIndex].value);
        };
        for (var ind in orderlist) {
            nextselect.options.add(new Option(orderlist[ind].name, orderlist[ind].id));
        }
        document.getElementById("IndustrySelect").parentNode.appendChild(nextselect);
        document.getElementById("Industry").value = nextselect.options[nextselect.selectedIndex].value;
    }else{
        document.getElementById("Industry").value = x;
    }
}

function compare(a,b){

   if(a.length == b.length){
       return false;
   }

    a = a.substring(0,2);
    b = b.substring(0,2);
    if(a == b){
        return true;
    }
    return false;
}


//this.options[this.selectedIndex].value