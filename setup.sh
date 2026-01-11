
if [[ "$setup_complete" != true ]]; then
    PATH="$(pwd)/bin:${PATH}"
    chmod +x ./bin/*
    setup_complete=true
fi



