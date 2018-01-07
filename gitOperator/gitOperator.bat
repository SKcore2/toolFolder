@echo off

cd /d %~dp0
echo %1

set a=3
if %1==Status (
echo its 1
git status > result.txt
) else if %1==Add (
git add %2 > result.txt
) else if %1==Commit (
echo its 3
)
