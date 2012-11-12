echo RX REPORT
cat area_report_rx.txt | grep ombinational
cat area_report_rx.txt | grep "Total area"
cat timing_report_rx.txt | grep "slack"

echo 
echo TX REPORT
cat area_report_tx.txt | grep ombinational
cat area_report_tx.txt | grep "Total area"
cat timing_report_tx.txt | grep "slack"
