<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Email Sender</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />

    <script src="//cdn.ckeditor.com/4.6.0/full-all/ckeditor.js"></script>

</head>
<body>
<div id="content" role="main">
    <section class="row colset-2-its">
        <g:if test="${flash.message}">
            <div class="message" role="alert">
                ${flash.message}
            </div>
        </g:if>
        <h2>Email Sender Form</h2>
        <g:form controller="emailSender" method="post" enctype="multipart/form-data" action="send">
            <div class="fieldcontain">
                <g:textField name="address" placeholder="youremail@djamware.com" required="required" />
            </div>
            <div class="fieldcontain">
                <g:textField name="subject" placeholder="Your Subject" required="required" />
            </div>
            <div class="fieldcontain">
                <g:textArea name="body" rows="5" cols="80" placeholder="Your message" required="required" />
            </div>
            <div class="fieldcontain">
                <input type="file" name="attachment" required="required" />
            </div>
            <fieldset>
                <g:submitButton name="send" value="Send" />
            </fieldset>
        </g:form>
    </section>
</div>
<script>
    CKEDITOR.replace('body', {
        extraPlugins: 'codesnippet'
    });
</script>
</body>
</html>