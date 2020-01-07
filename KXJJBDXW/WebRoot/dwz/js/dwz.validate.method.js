/**
 * @requires jquery.validate.js
 * @author ZhangHuihua@msn.com
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("alphanumeric", function(value, element) {
			return this.optional(element) || /^\w+$/i.test(value);
		}, "Letters, numbers or underscores only please");
		
		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z]+$/i.test(value);
		}, "Letters only please"); 
		
		$.validator.addMethod("phone", function(value, element) {
			return this.optional(element) || /^[0-9 \(\)]{7,30}$/.test(value);
		}, "Please specify a valid phone number");
		
		$.validator.addMethod("postcode", function(value, element) {
			return this.optional(element) || /^[0-9 A-Za-z]{5,20}$/.test(value);
		}, "Please specify a valid postcode");

      $.validator.addMethod("password", function(value, element) {
         return this.optional(element) || /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*-=_+\(\)])[A-Za-z\d!@#$%^&*-=_+\(\)]{8,}$/.test(value);
      }, "Letters, numbers or !@#$%^&*()-=_+ only please");
        
    	$.validator.addMethod("ratePercent", function(val, ele) {
    		return this.optional(ele) || /^((0\.[0-9]{1,2})|(1\.00)|1|0)$/.test(val);
    	}, "Please input 0~1");
    	
		$.validator.addMethod("date", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('dateFmt') || 'yyyy-MM-dd';
	
				return !$input.val() || $input.val().parseDate(pattern);
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "Please enter a valid date.");
		
		/*自定义js函数验证
		 * <input type="text" name="xxx" customvalid="xxxFn(element)" title="xxx" />
		 */
		$.validator.addMethod("customvalid", function(value, element, params) {
			try{
				return eval('(' + params + ')');
			}catch(e){
				return false;
			}
		}, "Please fix this field.");
		
		$.validator.addClassRules({
			date: {date: true},
			alphanumeric: { alphanumeric: true },
			lettersonly: { lettersonly: true },
			phone: { phone: true },
			postcode: {postcode: true},
         password: {password: true},
         ratePercent: {ratePercent: true}
		});
		$.validator.setDefaults({errorElement:"span"});
		$.validator.autoCreateRanges = true;
		
	}

})(jQuery);
