///
var parms = [];

function DbOperate(OperateList, fn, callback, finishFunc) {
    OperateList.i = OperateList.i || 0;

    var config = {
        timeout: 600000,
        url: "./ajax/AjaxData.aspx",
        data: { act: OperateList[OperateList.i].cmd, action: OperateList.action, table: OperateList[OperateList.i].table },
        async: true,
        success: function (text, options) {
            try {
                var result = eval("(" + text + ")");
                if (result.success == "true") {
                    if (result.operate == "NoData") {
                        fn("表[" + OperateList[OperateList.i].table + "]里没有数据");
                        OperateList.i = OperateList.i + 2;
                    }
                    else {
                        fn(OperateList[OperateList.i].success);
                        OperateList.i = OperateList.i + 1;
                    }

                    if (OperateList.i < OperateList.length) {
                        DbOperate(OperateList, fn, callback, finishFunc);
                    }
                    else {
                        fn("", true);
                        if (typeof callback == "function")
                            setTimeout(function () { callback.apply(this); }, 3000);
                        if (typeof finishFunc == "function")
                            setTimeout(function () { finishFunc.apply(this); }, 3000);
                        ///所有的操作完毕
                    }
                }
                else {
                    fn(result.desc, true);
                    if (typeof callback == "function")
                        setTimeout(function () { callback.apply(this); }, 3000);
                    if (typeof finishFunc == "function")
                        setTimeout(function () { finishFunc.apply(this); }, 3000);
                }
            }
            catch (ex) {
                fn(ex.Message, true);
            }
        },
        error: function (text, status) {
            if (status == "timeout") {
                fn("当前操作已操时！", true);
            }
            else {
                fn("应用程序错误！错误原因：" + text, true)
            }
        }
    }

    fn(OperateList[OperateList.i].start);

    jQuery.ajax(config);
}

function FsOperate(OperateList, fn, callback, finishFunc) {
    OperateList.i = OperateList.i || 0;

    var config = {
        timeout: 600000,
        url: "./ajax/AjaxData.aspx",
        data: { act: OperateList[OperateList.i].cmd, action: OperateList.action, name: OperateList[OperateList.i].fsName },
        async: true,
        success: function (text, options) {
            try {
                var result = eval("(" + text + ")");
                if (result.success == "true") {
                    fn(OperateList[OperateList.i].success);
                    OperateList.i = OperateList.i + 1;

                    if (OperateList.i < OperateList.length) {
                        FsOperate(OperateList, fn, callback, finishFunc);
                    }
                    else {
                        fn("", true);
                        if (typeof callback == "function")
                            setTimeout(function () { callback.apply(this); }, 3000);
                        if (typeof finishFunc == "function")
                            setTimeout(function () { finishFunc.apply(this); }, 3000);
                        ///所有的操作完毕
                    }
                }
                else {
                    fn(result.desc, true);
                    if (typeof callback == "function")
                        setTimeout(function () { callback.apply(this); }, 3000);
                    if (typeof finishFunc == "function")
                        setTimeout(function () { finishFunc.apply(this); }, 3000);
                }
            }
            catch (ex) {
                fn(ex.Message, true);
            }
        },
        error: function (text, status) {
            if (status == "timeout") {
                fn("当前操作已操时！", true);
            }
            else {
                fn("应用程序错误！错误原因：" + text, true)
            }
        }
    }

    fn(OperateList[OperateList.i].start);

    jQuery.ajax(config);
}

function getInstallList(action) {
    return parms;
}