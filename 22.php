<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // 目标目录
    $targetDir = "uploads/";
    $targetFile = $targetDir . basename($_FILES["fileToUpload"]["name"]);
    $uploadOk = 1;

    // 检查文件是否是实际的图片
    $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
    if ($check !== false) {
        echo "文件是一个图像 - " . $check["mime"] . ".";
        $uploadOk = 1;
    } else {
        echo "文件不是一个图像.";
        $uploadOk = 0;
    }

    // 检查是否存在错误
    if ($_FILES["fileToUpload"]["error"] !== UPLOAD_ERR_OK) {
        echo "上传错误: " . $_FILES["fileToUpload"]["error"];
        $uploadOk = 0;
    }

    // 检查文件大小
    if ($_FILES["fileToUpload"]["size"] > 500000) {
        echo "抱歉，文件太大.";
        $uploadOk = 0;
    }

    // 允许特定文件格式
    $imageFileType = strtolower(pathinfo($targetFile, PATHINFO_EXTENSION));
    if ($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg" && $imageFileType != "gif") {
        echo "抱歉，只允许 JPG, JPEG, PNG & GIF 文件.";
        $uploadOk = 0;
    }

    // 检查 $uploadOk 是否为 0 表示错误
    if ($uploadOk == 0) {
        echo "抱歉，您的文件未被上传.";
    } else {
        if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $targetFile)) {
            echo "文件 ". htmlspecialchars(basename($_FILES["fileToUpload"]["name"])) . " 已成功上传.";
        } else {
            echo "抱歉，上传时发生错误.";
        }
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>文件上传</title>
</head>
<body>
    <form action="" method="post" enctype="multipart/form-data">
        选择文件上传:
        <input type="file" name="fileToUpload" id="fileToUpload" required>
        <input type="submit" value="上传文件" name="submit">
    </form>
</body>
</html>
