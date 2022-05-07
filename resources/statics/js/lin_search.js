function Search(objId, inputId, color) {
    this.tableSearch = function () {
        $('#content-null').remove();
        this.objId.find('tr span').css({
            'color': "black",
            'font-weight': 'normal'
        });

        var tableTrTdContent = this.objId.find('tr td:contains("' + this.inpIdContents + '")');
        if (this.inpIdContents != '') {
            if (tableTrTdContent.length == 0) {
                this.objId.find('tr:not(:eq(0))').css({
                    display: "none"
                })
                var tableColspanNumber = this.objId.find('tr').eq(0).find('th').length || this.objId.find('tr').eq(0).find('td').length;
                var tr = $(`
                    <tr id="content-null">
                    <td colspan='${tableColspanNumber}' style="text-align: center;">No search content yet</td>
                    </tr>
                    `);
                this.objId.append(tr)
            } else if (tableTrTdContent.length > 0) {
                $('#content-null').remove();
                this.objId.find('tr:not(:eq(0))').css({
                    display: "none"
                })

                for (var a = 0; a < tableTrTdContent.length; a++) {
                    // console.log(tableTrTdContent[a].parentNode)
                    tableTrTdContent[a].parentNode.style.display = "table-row";
                    var contents = tableTrTdContent.eq(a).text();
                    var contentsArr = contents.split(this.inpIdContents);
                    var contentArrFirst = contentsArr[0];
                    for (var j = 1; j < contentsArr.length; j++) {
                        //java.net.URLEncoder.encode(contents,"utf-8");
                        contentArrFirst += `<a href='indexAfterLogin.html?data=` + encodeURIComponent(contents) + `'><span style=';color:${this.color};font-weight:bolder'>` + this.inpIdContents + "</span>" + contentsArr[j] + "</a>";
                    }
                    ;
                    tableTrTdContent.eq(a).html(contentArrFirst);
                    console.log(tableTrTdContent.length - 1)
                }
            }
        } else {
            this.objId.find('tr:not(:eq(0))').css({
                display: "table-row"
            });
            $('#content-null').remove();
        }
    }

    this.init = function () {
        this.color = color || 'red';
        console.log(this.color)
        if (typeof $ == "undefined") {
            throw new Error("鐠囥儲鎮崇槐銏犲閼虫垝绶风挧鏍︾艾jquery閹绘帊娆㈤敍宀勬付鐟曚礁绱╅崗顧皅uery");
        }

        if (typeof objId[0] == "undefined") {
            this.objId = $(objId);

        } else {
            this.objId = objId;

        }

        if (typeof inputId[0] == "undefined") {
            var inp = $(inputId);
        } else {
            var inp = inputId;
        }

        if (inp[0].tagName == "SELECT") {
            if (inp.val().trim() == inp.find('option:first').val()) {
                this.inpIdContents = '';
            } else {
                this.inpIdContents = inp.val().trim();
            }
        } else {
            this.inpIdContents = inp.val().trim()
        }
        this.objType = this.objId[0].tagName;
        console.log(this.objType)
        switch (this.objType) {
            case 'TABLE':
                this.tableSearch();
                break;
            case 'UL':
                this.ulSearch();
                break;
        }
    }
    this.init()
}