用于服务器端接收表单数据并解析，具体功能为：
1.参数检查未通过时，返回对应的code，提示客户端出错位置；
2.参数检查通过后，在数据库对应的表中新插入客户端提交的表单信息，并返回成功code