Object.prototype.toJava = function() {
  var m = new java.util.HashMap();
  for (var key in this)
    if (this.hasOwnProperty(key))
      m.put(key, this[key].toJava());
  return m;
};
Array.prototype.toJava = function() {
  var l = this.length;
  var a = new java.lang.reflect.Array.newInstance(java.lang.Object, l);
  for (var i = 0;i < l;i++)
    a[i] = this[i].toJava();
  return a;
};
String.prototype.toJava = function() {
  return new java.lang.String(this);
};
Boolean.prototype.toJava = function() {
  return java.lang.Boolean.valueOf(this);
};
Number.prototype.toJava = function() {
  if(this.valueOf() != this.toFixed(0).valueOf()){
  		return java.lang.Double(this);
  }else if(this.valueOf()<java.lang.Integer.MAX_VALUE && this.valueOf()>java.lang.Integer.MIN_VALUE){
  		return java.lang.Integer(this);
  }else{
  		return java.lang.Long(this);
  }
};