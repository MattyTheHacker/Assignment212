#!/bin/bash

# directory for compilation output and failure reports
WORK_DIRECTORY=testing

echo "Note: Please do not rely solely on this script for your testing. (I don't want to be responsible for you losing marks!)"
echo

[ -e $WORK_DIRECTORY ] && rm -r $WORK_DIRECTORY
mkdir -p $WORK_DIRECTORY
echo "Compiling source files..."
echo
if ! javac src/*.java -d $WORK_DIRECTORY ; then
    echo "Compilation failed. Aborting."
    exit 1
fi

run_test () {
    cat testInputs/"${1}"TestInput.txt | java -cp $WORK_DIRECTORY BingoRunner | diff expectedOutputs/"${1}"ExpectedOutput.txt - > /dev/null 2>&1
}

run_detailed_test () {
    mkdir -p "${WORK_DIRECTORY}"/failures/"${1}"/
    cat testInputs/"${1}"TestInput.txt | java -cp $WORK_DIRECTORY BingoRunner > "${WORK_DIRECTORY}"/failures/"${1}"/ProgramOutput.txt 2>&1
    cp expectedOutputs/"${1}"ExpectedOutput.txt "${WORK_DIRECTORY}"/failures/"${1}"/ExpectedOutput.txt
    # note: you may want to add the -u switch here if you do not understand the diff output
    diff "${WORK_DIRECTORY}"/failures/"${1}"/ExpectedOutput.txt "${WORK_DIRECTORY}"/failures/"${1}"/ProgramOutput.txt  > "${WORK_DIRECTORY}"/failures/"${1}"/Diff.txt
}

rewrite_status () {
   printf '\033[1A'
   echo -n "Test $1 "
   printf "[$2]\n"
}

PASSES=0
FAILURES=0

for file in testInputs/*; do
    FILENAME="$(basename "$file")"
    CODE=${FILENAME:0:3}
    echo "Test $CODE"

    if [ ! -e expectedOutputs/"${CODE}"ExpectedOutput.txt ]; then
         rewrite_status "$CODE" "\033[0;37mSkipped\033[0m"
         echo " No output file exists for test $CODE"
         continue
    fi

    if run_test "$CODE"; then
        rewrite_status "$CODE" "\033[0;32m\xE2\x9C\x94\033[0m"
        ((PASSES++))
    else
        rewrite_status "$CODE" "\033[0;31mFailed\033[0m"
        ((FAILURES++))
        echo " Output of test $CODE was not an exact match"
        run_detailed_test "$CODE"
        echo " Inspect $WORK_DIRECTORY/failures/$CODE/ for more details"
    fi
done

echo
echo "Tests complete with ${PASSES} passes and ${FAILURES} failures."

if (( FAILURES > 0 )); then
    exit 2
fi
