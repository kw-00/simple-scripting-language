
if [[ "$setup_complete" != true ]]; then
    PATH="$(pwd)/bin:${PATH}"
    chmod +x ./bin/*
    setup_complete=true
    echo "Environment set up successfully."
else
    echo "Environment already set up."
fi



